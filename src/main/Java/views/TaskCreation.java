package views;

import controllers.TasksController;
import models.MutableTask;
import models.Task;
import models.User;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class TaskCreation extends JDialog {
    private JPanel contentPane;
    private JButton buttonAdd;
    private JButton buttonCancel;
    private JTextField dateField;
    private JTextArea descriptionArea;
    private JTextField nameField;

    private JFrame frame;
    private TasksController controller;
    private User user;

    public TaskCreation(JFrame frame, TasksController controller, User user) {
        super(frame, "Task creation", true);
        this.frame = frame;
        this.controller = controller;
        this.user = user;
        setSize(new Dimension(500, 350));
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        getRootPane().setDefaultButton(buttonAdd);

        // TODO check or refactor
        while(true) {
            try {
                setContentPane(contentPane);
                break;
            }
            catch (NullPointerException ex) {
                System.out.println("Null Pointer in TaskCreation constructor");
            }
        }
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy dd.MM HH:mm");
        LocalDateTime dueDate = LocalDateTime.now();

        if(nameField.getText().length() == 0 || nameField.getText().length() > 24 ||
                controller.getModel().getJournal().entrySet().stream().anyMatch(task ->
                        task.getValue().getName().toLowerCase().equals(nameField.getText().toLowerCase())))
            new ErrorDialog(frame, ErrorType.NAME_UNIQUENESS_OR_LENGTH);
        else if(descriptionArea.getText().length() > 256)
            new ErrorDialog(frame, ErrorType.DESCRIPTION_LENGTH);
        else {
            boolean dateError = false;
            try {
                dueDate = LocalDateTime.parse(dateField.getText(), formatter);
            }
            catch (DateTimeParseException e) {
                dateError = true;
                new ErrorDialog(frame, ErrorType.DATE_FORMAT);
            }

            if(!dateError && LocalDateTime.now().compareTo(dueDate) >= 0) {
                dateError = true;
                new ErrorDialog(frame, ErrorType.DATE_ALREADY_PAST);
            }

            if(!dateError) {
                controller.add(new MutableTask(UUID.randomUUID(), nameField.getText(), descriptionArea.getText(),
                        LocalDateTime.now(), LocalDateTime.parse(dateField.getText(), formatter),
                        user.getId(), "In process"));
                dispose();
            }
        }
    }

    private void onCancel() {
        dispose();
    }

}
