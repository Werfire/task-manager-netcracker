package views;

import controllers.TaskController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class TaskCreation extends JDialog {
    private JPanel contentPane;
    private JButton buttonAdd;
    private JButton buttonCancel;
    private JTextField dateField;
    private JTextArea descriptionArea;
    private JTextField nameField;

    public TaskCreation(JFrame frame) {
        super(frame, "Task creation", true);
        setContentPane(contentPane);
        setSize(new Dimension(500, 350));
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        getRootPane().setDefaultButton(buttonAdd);

        descriptionArea.setBorder(UIManager.getBorder("TextField.border"));

        buttonAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void onOK() {
        if(nameField.getText().length() == 0 || nameField.getText().length() > 24)
            new InputError((JFrame)getParent(), InputErrorType.NAME_LENGTH);
        else if(descriptionArea.getText().length() > 256)
            new InputError((JFrame)getParent(), InputErrorType.DESCRIPTION_LENGTH);
        // TODO Due date checks
        else {

            dispose();
        }
    }

    private void onCancel() {
        dispose();
    }

}
