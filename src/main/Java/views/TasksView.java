package views;
import javax.inject.Inject;
import javax.swing.*;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;

import controllers.UsersController;
import interfaces.TasksObserver;
import models.MutableTask;
import models.User;
import util.NotificationsScheduler;
import controllers.TasksController;


public class TasksView extends JFrame implements TasksObserver {
    private JPanel mainPanel;
    private JTable tasksTable;
    private JButton addButton;
    private JButton completeButton;
    private JButton deleteButton;
    private JScrollPane scrollPane;
    private JPanel buttons;
    private JPanel journal;
    private JLabel title;

    private UsersController usersController;
    private TasksController tasksController;
    private List<UUID> tasksIDs = new ArrayList<>();
    private User user;
    private int selectedColumn;
    private int selectedRow;

    public TasksView(UsersController usersController, TasksController tasksController, User user) {
        add(mainPanel);
        setTitle("Task Manager");
        setSize( 600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.X_AXIS));

        this.usersController = usersController;
        this.tasksController = tasksController;
        tasksController.mainFrame = this;
        tasksController.getModel().addObserver(this);
        this.user = user;

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                tasksController.getModel().removeObserver(TasksView.this);
                tasksController.writeToFile();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    new TaskCreation(TasksView.this, tasksController, user);
            }
        });
        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tasksController.changeStatus(tasksIDs.get(tasksTable.convertRowIndexToModel(selectedRow)), "Completed");
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tasksController.delete(tasksIDs.get(tasksTable.convertRowIndexToModel(selectedRow)));
            }
        });
        deleteButton.setEnabled(false);
        completeButton.setEnabled(false);

        tasksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tasksTable.getTableHeader().setReorderingAllowed(false);

        ListSelectionModel selectionModel = tasksTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!selectionModel.isSelectionEmpty()) {
                    selectedColumn = tasksTable.getSelectedColumn();
                    selectedRow = tasksTable.getSelectedRow();
                    if(!tasksController.get(tasksIDs.get(tasksTable.convertRowIndexToModel(selectedRow))).getStatusId()
                            .equals("Completed"))
                        completeButton.setEnabled(true);
                    else
                        completeButton.setEnabled(false);
                    deleteButton.setEnabled(true);
                }
                else {
                    completeButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                }
            }
        });

        scrollPane = new JScrollPane(tasksTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));
        journal.setLayout(new BoxLayout(journal,BoxLayout.Y_AXIS));
        journal.add(title);
        journal.add(scrollPane);
        buttons.setLayout(new BoxLayout(buttons,BoxLayout.Y_AXIS));
        buttons.add(addButton);
        buttons.add(Box.createVerticalGlue());
        buttons.add(completeButton);
        buttons.add(Box.createVerticalGlue());
        buttons.add(deleteButton);
        getContentPane().add(buttons);
        getContentPane().add(journal);
//        pack();

        //tasksController.readFromFile();
        tasksController.updateJournalFromServer();
        update(tasksController.getModel().getJournal());
        setVisible(true);
    }

    @Override
    public void update(HashMap<UUID, MutableTask> journal) {
        tasksIDs = new ArrayList<>();
        NotificationsScheduler.resetTimers();
        TasksTableModel tableModel = new TasksTableModel();
        for(HashMap.Entry<UUID, MutableTask> entry : journal.entrySet()) {
            tasksIDs.add(entry.getValue().getId());

            tableModel.addRow(entry.getValue().toStringArray());
            NotificationsScheduler.scheduleNotifications(this, entry.getValue());
        }
        tableModel.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                String value = tasksTable.getModel().getValueAt(selectedRow, selectedColumn).toString();
                switch(selectedColumn) {
                    case 0:
                        if(value.length() == 0 || value.length() > 24 ||
                                tasksController.getModel().getJournal().entrySet().stream().anyMatch(task ->
                                        task.getValue().getName().toLowerCase().equals(value.toLowerCase()))) {
                            new ErrorDialog((JFrame) getParent(), ErrorType.NAME_UNIQUENESS_OR_LENGTH);
                            update(tasksController.getModel().getJournal());
                        }
                        else
                            tasksController.editName(tasksIDs.get(tasksTable.convertRowIndexToModel(selectedRow)), value);
                        break;
                    case 1:
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy dd.MM HH:mm");
                        LocalDateTime dueDate = LocalDateTime.now();
                        boolean error = false;
                        try {
                            dueDate = LocalDateTime.parse(value, formatter);
                        }
                        catch (DateTimeParseException exception) {
                            error = true;
                            new ErrorDialog(TasksView.this, ErrorType.DATE_FORMAT);
                            update(tasksController.getModel().getJournal());
                        }

                        if(!error && LocalDateTime.now().compareTo(dueDate) >= 0) {
                            new ErrorDialog(TasksView.this, ErrorType.DATE_ALREADY_PAST);
                            update(tasksController.getModel().getJournal());
                        }

                        if(!error)
                            tasksController.editDueDate(tasksIDs.get(tasksTable.convertRowIndexToModel(selectedRow)), dueDate);
                        break;
                    case 2:
                        if(value.length() > 256) {
                            new ErrorDialog(TasksView.this, ErrorType.DESCRIPTION_LENGTH);
                            update(tasksController.getModel().getJournal());
                        }
                        else
                            tasksController.editDescription(tasksIDs.get(tasksTable.convertRowIndexToModel(selectedRow)), value);
                        break;

                }
            }
        });
        tasksTable.setModel(tableModel);
    }
}


