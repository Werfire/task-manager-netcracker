package views;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TasksView extends JFrame {

    private JPanel mainPanel;
    private JTable table1;
    private JButton DELETEButton;
    private JButton ADDButton;
    private JButton EDITButton;
    private JButton COMPLETEButton;


    public TasksView() {
        add(mainPanel);
        setTitle("Task Manager");
        setSize( 800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // нельзя менять размеры окна

        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
