package views;

import controllers.TasksController;
import models.MutableTask;
import models.User;
import util.ErrorType;

import javax.swing.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
        //contentPane.setLayout(new GridBagLayout());
        this.frame = frame;
        this.controller = controller;
        this.user = user;
        setSize(new Dimension(500, 350));
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        getRootPane().setDefaultButton(buttonAdd);
        setContentPane(contentPane);
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
                MutableTask task = new MutableTask(UUID.randomUUID(), nameField.getText(), descriptionArea.getText(),
                        LocalDateTime.now(), LocalDateTime.parse(dateField.getText(), formatter),
                        user.getId(), "In process");
                controller.add(task);
                Client client = ClientBuilder.newClient();
                WebTarget webTarget = client.target("http://localhost:8080/rest").path("api/tasks");
                webTarget.request(MediaType.TEXT_PLAIN).post(Entity.json(task));
                dispose();
            }
        }
    }

    private void onCancel() {
        dispose();
    }

}
