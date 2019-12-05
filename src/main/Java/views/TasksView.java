package views;
import controllers.TaskController;
import models.Task;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

public class TasksView extends JFrame {
    public final TaskController controller = new TaskController(this);

    private JPanel mainPanel;
    private JTable tasksTable;
    private JButton completeButton;
    private JButton deleteButton;
    private JButton addButton;
    private JButton editButton;

    public TasksView() {
        add(mainPanel);
        setTitle("Task Manager");
        setSize( 800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new TaskCreation(TasksView.this);
            }
        });

        setLocationRelativeTo(null);

        controller.add(new Task(UUID.randomUUID(), "Лаба", "Доделать шестую лабу.",
                LocalDateTime.of(2019, Month.NOVEMBER, 25, 18, 45),
                LocalDateTime.now().plusSeconds(80), 47, 2));
    }
}
