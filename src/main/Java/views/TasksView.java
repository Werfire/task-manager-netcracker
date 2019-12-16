package views;
import javax.swing.*;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

import interfaces.TasksObserver;
import models.MutableTask;
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

    private List<UUID> tasksIDs = new ArrayList<>();
    private Set<TableModelListener> listeners = new HashSet<>();
    private String[] columns = {"Name", "DueDate", "Description", "Author", "StatusID"};
    private int selectedColumn;
    private int selectedRow;

    public TasksView(TasksController controller) {
        add(mainPanel);
        setTitle("Task Manager");
        setSize( 600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
        controller.mainFrame = this;
        controller.getModel().addObserver(this);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TaskCreation(TasksView.this, controller);
            }
        });
        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //controller.changeStatus(tasks.get(tasksTable.convertRowIndexToModel(selectedRow)), 1);
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
                    completeButton.setEnabled(true);
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
//        getContentPane().setLayout(new GridLayout(1,2,0,0));
        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.X_AXIS));
//        GroupLayout layout = new GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setAutoCreateGaps(true);
//        layout.setAutoCreateContainerGaps(true);
//        layout.setHorizontalGroup(layout.createSequentialGroup()
//                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//                        .addComponent(addButton)
//                        .addComponent(completeButton)
//                        .addComponent(deleteButton))
//                 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//                        .addComponent(title)
//                        .addComponent(scrollPane))
//                );
//        layout.linkSize(SwingConstants.HORIZONTAL, addButton, completeButton,deleteButton);
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
    }

    @Override
    public void update(HashMap<UUID, MutableTask> journal) {
        tasksIDs = new ArrayList<>();
        NotificationsScheduler.resetTimers();
        DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, columns);
        for(Map.Entry<UUID, MutableTask> entry : journal.entrySet()) {
            tasksIDs.add(entry.getValue().getId());
            tableModel.addRow(entry.getValue().toStringArray());
            NotificationsScheduler.scheduleNotifications(this, entry.getValue());
        }
        tasksTable.setModel(tableModel);
    }
}

