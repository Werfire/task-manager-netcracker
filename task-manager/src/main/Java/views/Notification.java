package views;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import models.Task;

public class Notification extends JDialog {
    public Notification(JFrame frame, Task task, boolean premessage) {
        super(frame, String.format("Notification (%s)", LocalDateTime.now().toString()), true);
        Container pane = getContentPane();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(450, 120));
        setResizable(false);
        setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        String message;
        if(premessage)
            message = String.format("After 1 minute, the deadline for the \"%s\" task will come.", task.getName());
        else
            message = String.format("The execution time for the \"%s\" task has come to an end.", task.getName());
        JLabel label = new JLabel(message);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        pane.add(new Box.Filler(new Dimension(0, 0),
                new Dimension(0, 20), new Dimension(0, 20)));
        pane.add(label);

        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(e -> dispose());
        btnOk.setAlignmentX(Component.CENTER_ALIGNMENT);
        pane.add(new Box.Filler(new Dimension(0, 0),
                new Dimension(0, 10), new Dimension(0, 10)));
        pane.add(btnOk);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
