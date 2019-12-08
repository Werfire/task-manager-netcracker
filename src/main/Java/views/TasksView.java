package views;
import javax.annotation.Nonnull;
import javax.swing.*;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import models.Task;
import models.TaskModel;
import controllers.TaskController;


public class TasksView  extends JFrame {


    public  TaskController controller = new TaskController(this);
    private JPanel mainPanel;
    private JTable journal;
    private JButton completeButton;
    private JButton deleteButton;
    private JButton addButton;
    private JButton editButton;
    private JScrollPane scrollPane;
    private JPanel buttons;
    private TableModel mod;
    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private TaskModel temp;
    private Object[] cols = {"Name","Date","Description","Author","Status"};
    public TasksView() {
        add(mainPanel);
        setTitle("Task Manager");
        setSize( 700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new TaskCreation(TasksView.this);
            }
        });
        temp = new TaskModel(controller.read());
        DefaultTableModel tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };

        tableModel.setColumnIdentifiers(cols);
        controller.add(new Task(UUID.randomUUID(), "Лаба", "Доделать шестую лабу.",
                LocalDateTime.of(2019, Month.NOVEMBER, 25, 18, 45),
                LocalDateTime.now().plusSeconds(80), UUID.randomUUID(), 2));
//            int i = 0;
//            tableModel.addRow( new String[]{"Name","Descrip","DueDate","Avtor","Statuse"});
        for(HashMap.Entry<UUID, Task> entry : controller.getModel().getJournal().entrySet()) {
            Task curTask = entry.getValue();
            tableModel.addRow(new String[] {curTask.getName(),curTask.getDueDate().toString(),
                    curTask.getDescription(),curTask.getAuthorId().toString(),String.valueOf(curTask.getStatusId())});
//            tableModel.setValueAt(curTask.getName(),i,0);
//            tableModel.setValueAt(curTask.getDueDate(),i,1);
//            tableModel.setValueAt(curTask.getDescription(),i,2);
//            tableModel.setValueAt(curTask.getAuthorId(),i,3);
//            tableModel.setValueAt(curTask.getStatusId(),i,4);
//            ++i;
        }
        journal.setModel(tableModel);
            scrollPane = new JScrollPane(journal,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.LINE_AXIS));
            buttons.setLayout(new BoxLayout(buttons,BoxLayout.Y_AXIS));
            buttons.add(addButton);
            buttons.add(Box.createVerticalGlue());
            buttons.add(completeButton);
            buttons.add(Box.createVerticalGlue());
            buttons.add(deleteButton);
            getContentPane().add(scrollPane);
            getContentPane().add(buttons);
//            this.pack();

    }


}

