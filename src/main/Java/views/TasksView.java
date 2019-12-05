package views;
import javax.swing.*;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private TableModel mod;
    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private TaskModel temp;
    Object[] cols = {"Name","Date","Description","Author","Status"};
    public TasksView() {
        add(mainPanel);
//       mainPanel.setLayout(new FlowLayout());

        setTitle("Task Manager");
        setSize( 800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // нельзя менять размеры окна
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
                LocalDateTime.now().plusSeconds(80), 47, 2));
            int i = 0;
        for(HashMap.Entry<UUID, Task> entry : temp.getJournal().entrySet()) {
            tableModel.setValueAt(entry.getValue().getName(),i,0);
            tableModel.setValueAt(entry.getValue().getDueDate(),i,1);
            tableModel.setValueAt(entry.getValue().getDescription(),i,2);
            tableModel.setValueAt(entry.getValue().getAuthorId(),i,3);
            tableModel.setValueAt(entry.getValue().getStatusId(),i,4);
            ++i;
        }
        journal.setModel(tableModel);
//        JScrollPane scrollPane = new JScrollPane(journal);
//        scrollPane.setLayout(new ScrollPaneLayout());
//        mainPanel.add(scrollPane);



    }



}
