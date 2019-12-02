package views;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private JPanel mainPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTable table1;
    private JButton addTaskButton;

    public GUI() {
        add(mainPanel);
        setTitle("Task Manager");
        setSize( 400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false); // нельзя менять размеры окна

        addTaskButton.addActionListener(new ActionListener() {     // нажатие кнопки
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }
}
