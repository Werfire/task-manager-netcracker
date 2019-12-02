package views;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TasksView extends JFrame {

    private JPanel mainPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTable table1;
    private JButton addTaskButton;

    public TasksView() {
        add(mainPanel);
        setTitle("Task Manager");
        setSize( 400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // нельзя менять размеры окна

        addTaskButton.addActionListener(new ActionListener() {     // нажатие кнопки
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }
}
