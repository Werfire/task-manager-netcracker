package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class ErrorDialog extends JDialog {
    private static final String[] errors = new String[] {
            "Task's name must be unique and 1 to 24 characters long.",
            "Task's description must not be longer than 256 characters.",
            "Due date's input format is incorrect (e.g. \"2019 31.12 12:30\").",
            "Due date is already in the past.",
            "Something went wrong with writing/reading of file.",
            "This type of data is not supported.",
            "Wrong username or password.",
            "This username already taken.",
            "Username must be 3 to 12 characters long.",
            "Password must be 4 to 18 characters long.",
            "Different password in \"Confirm password\" field.",
            "Some system error."

    };

    public ErrorDialog(JFrame frame, ErrorType errorType) {
        super(frame,true);
        if(errorType.getErrNumber() <= 3)
            setTitle("User input error");
        else if(errorType.getErrNumber() <= 5)
            setTitle("File IO error");
        else if(errorType.getErrNumber() <= 6)
            setTitle("Login error");
        else if(errorType.getErrNumber() <= 10)
            setTitle("Registration error");
        else
            setTitle("Error");
        JPanel pane = (JPanel)getContentPane();
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

        pane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

