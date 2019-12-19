package views;
import javax.swing.*;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;

import interfaces.TasksObserver;
import models.MutableTask;
import models.TasksTableModel;
import models.User;
import util.NotificationsScheduler;
import models.Task;
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

    private TasksController controller;
    private List<UUID> tasksIDs = new ArrayList<>();
    private User user;
    private int selectedColumn;
    private int selectedRow;

    public TasksView(TasksController _controller, User user) {
        add(mainPanel);
        setTitle("Task Manager");
        setSize( 600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.X_AXIS));
        controller = _controller;
        controller.mainFrame = this;
        controller.getModel().addObserver(this);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                controller.write();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TaskCreation(TasksView.this, controller);
            }
        });
        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.changeStatus(tasksIDs.get(tasksTable.convertRowIndexToModel(selectedRow)), 1);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.delete(tasksIDs.get(tasksTable.convertRowIndexToModel(selectedRow)));
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
                    if(controller.get(tasksIDs.get(tasksTable.convertRowIndexToModel(selectedRow))).getStatusId() != 1)
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

        controller.read();
        update(controller.getModel().getJournal());
        setVisible(true);
    }

    @Override
    public void update(HashMap<UUID, MutableTask> journal) {
        tasksIDs = new ArrayList<>();
        NotificationsScheduler.resetTimers();
        TasksTableModel tableModel = new TasksTableModel();
        for(HashMap.Entry<UUID, MutableTask> entry : journal.entrySet()) {
            if(!entry.getValue().getAuthorId().equals(user.getId()))
                continue;
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
                                controller.getModel().getJournal().entrySet().stream().anyMatch(task ->
                                        task.getValue().getName().toLowerCase().equals(value.toLowerCase()))) {
                            new ErrorDialog((JFrame) getParent(), ErrorType.NAME_UNIQUENESS_OR_LENGTH);
                            update(controller.getModel().getJournal());
                        }
                        else
                            controller.editName(tasksIDs.get(tasksTable.convertRowIndexToModel(selectedRow)), value);
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
                            update(controller.getModel().getJournal());
                        }

                        if(!error && LocalDateTime.now().compareTo(dueDate) >= 0) {
                            new ErrorDialog(TasksView.this, ErrorType.DATE_ALREADY_PAST);
                            update(controller.getModel().getJournal());
                        }

                        if(!error)
                            controller.editDueDate(tasksIDs.get(tasksTable.convertRowIndexToModel(selectedRow)), dueDate);
                        break;
                    case 2:
                        if(value.length() > 256) {
                            new ErrorDialog(TasksView.this, ErrorType.DESCRIPTION_LENGTH);
                            update(controller.getModel().getJournal());
                        }
                        else
                            controller.editDescription(tasksIDs.get(tasksTable.convertRowIndexToModel(selectedRow)), value);
                        break;
                }
            }
        });
        tasksTable.setModel(tableModel);
    }
}


