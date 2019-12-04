package views;

import javax.swing.*;
import java.awt.*;

public class InputError extends JDialog {
    private static final String[] errors = new String[] {
            "Task's name must be unique and 1 to 23 characters long.",
            "Task's description must not be longer than 128 characters.",
            "Due date's input format is incorrect (e.g. \"2019 31.12 12:30\").",
            "Due date is already in the past."
    };

    public InputError(JFrame frame, InputErrorType errorType) {
        super(frame, "Input error", true);
        Container pane = getContentPane();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(450, 120));
        setResizable(false);
        setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(errors[errorType.getErrNumber()]);
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

