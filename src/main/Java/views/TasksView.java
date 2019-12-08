package views;
import javax.swing.*;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.metal.MetalButtonUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

import interfaces.TasksObserver;
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

    private List<Task> tasks = new ArrayList<>();
    private Set<TableModelListener> listeners = new HashSet<>();
    private String[] columns = {"Name", "DueDate", "Description", "Author", "StatusID"};
    private int selectedColumn;
    private int selectedRow;

    public TasksView(TasksController controller) {
        add(mainPanel);
        setTitle("Task Manager");
        setSize( 700, 500);
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
                //controller.changeStatus(tasks.get(tasksTable.convertRowIndexToModel(selectedRow)).getId(), 1);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.delete(tasks.get(tasksTable.convertRowIndexToModel(selectedRow)).getId());
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

        controller.read();
        update(controller.getModel().getJournal());
    }

    @Override
    public void update(HashMap<UUID, Task> journal) {
        tasks = new ArrayList<>();
        DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, columns);
        for(HashMap.Entry<UUID, Task> entry : journal.entrySet()) {
            tasks.add(entry.getValue());
            tableModel.addRow(entry.getValue().toStringArray());
            NotificationsScheduler.scheduleNotifications(this, entry.getValue());
        }
        tasksTable.setModel(tableModel);
    }
}

