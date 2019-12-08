package views;
import javax.swing.*;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import interfaces.TasksObserver;
import util.NotificationsScheduler;
import models.Task;
import controllers.TasksController;


public class TasksView extends JFrame implements TasksObserver {

    private JPanel mainPanel;
    private JTable tasksTable;
    private JButton completeButton;
    private JButton deleteButton;
    private JButton addButton;
    private JScrollPane scrollPane;
    private JPanel buttons;
    private Set<TableModelListener> listeners = new HashSet<>();
    private String[] columns = {"Name", "DueDate", "Description", "Author", "Status"};

    public TasksView(TasksController controller) {
        add(mainPanel);
        setTitle("Task Manager");
        setSize( 700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);

        controller.mainFrame = this;
        controller.getModel().addObserver(this);
        for(HashMap.Entry<UUID, Task> entry : controller.getModel().getJournal().entrySet()) {
            NotificationsScheduler.scheduleNotifications(this, entry.getValue());
        }

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new TaskCreation(TasksView.this, controller);
            }
        });

        DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, columns);
        controller.read();

        for(HashMap.Entry<UUID, Task> entry : controller.getModel().getJournal().entrySet()) {
            tableModel.addRow(entry.getValue().toStringArray());
//            tableModel.setValueAt(curTask.getName(),i,0);
//            tableModel.setValueAt(curTask.getDueDate(),i,1);
//            tableModel.setValueAt(curTask.getDescription(),i,2);
//            tableModel.setValueAt(curTask.getAuthorId(),i,3);
//            tableModel.setValueAt(curTask.getStatusId(),i,4);
//            ++i;
        }
        tasksTable.setModel(tableModel);
        scrollPane = new JScrollPane(tasksTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.LINE_AXIS));
        buttons.setLayout(new BoxLayout(buttons,BoxLayout.Y_AXIS));
        buttons.add(addButton);
        buttons.add(Box.createVerticalGlue());
        buttons.add(completeButton);
        buttons.add(Box.createVerticalGlue());
        buttons.add(deleteButton);
        getContentPane().add(scrollPane);
        getContentPane().add(buttons);
//      pack();

    }

    @Override
    public void update(HashMap<UUID, Task> journal) {
        DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, columns);
        for(HashMap.Entry<UUID, Task> entry : journal.entrySet()) {
            tableModel.addRow(entry.getValue().toStringArray());
            NotificationsScheduler.scheduleNotifications(this, entry.getValue());
        }
        tasksTable.setModel(tableModel);
    }
}

