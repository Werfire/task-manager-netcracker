package views;
import javax.swing.*;
import javax.swing.plaf.metal.MetalBorders;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TasksView extends JFrame {

    private JPanel mainPanel;
    private JTextField taskNameField;
    private JTextField dueDateField;
    private JTable tasksTable;
    private JButton addTaskButton;

    public TasksView() {
        add(mainPanel);
        setTitle("Task Manager");
        setSize( 550, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        //TableModel tableModel =
        //        new DefaultTableModel(new Object[][] {{"Задача", "Сделать задачу.", "2019 12.12 12:00"}},
        //                new String[] {"Name", "Description", "Due date"});
        //tasksTable.setModel(tableModel);

        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });

        setLocationRelativeTo(null);
    }
}
