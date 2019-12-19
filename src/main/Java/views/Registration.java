package views;

import controllers.TasksController;
import models.User;
import models.UsersModel;
import controllers.UsersController;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class Registration extends JFrame {

        private String login;
        private String password;
        private JTextField passwordField = new JTextField();
        private JTextField loginField = new JTextField();
        private JTextField confirm = new JTextField();
        private UsersModel usersModel;
        UsersController uscon;
        TasksController tascon;
        private JFrame frame = new JFrame("Registration");
        public Registration(LoginView view, UsersController usersController,TasksController tasksController) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {
            super();
            uscon = usersController;
            tascon = tasksController;
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            JLabel hello = new JLabel("Input your login and password: " );
            frame.setVisible(true);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(450, 120);
            frame.setResizable(false);
            usersModel = uscon.getModel();
            Box box1 = Box.createHorizontalBox();
            JLabel username = new JLabel("Username: ");
            loginField = new JTextField(24);
            box1.add(username);
            box1.add(Box.createHorizontalStrut(20));
            box1.add(loginField);

            Box box2 = Box.createHorizontalBox();
            JLabel passLabel = new JLabel("Password:");
            passwordField = new JPasswordField(24);
            box2.add(passLabel);
            box2.add(Box.createHorizontalStrut(20));
            box2.add(passwordField);

            Box box21 = Box.createHorizontalBox();
            JLabel confLabel = new JLabel("Confirm password:");
            confirm = new JPasswordField(24);
            box21.add(confLabel);
            box21.add(Box.createHorizontalStrut(20));
            box21.add(confirm);

            Box box3 = Box.createHorizontalBox();
            JButton logIn = new JButton("Start using");
            logIn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        onOK(view);
                    } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IOException | IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            JButton cancel = new JButton("Cancel");
            cancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    view.setVisible(true);
                    onCancel();

                }
            });
            box3.add(Box.createHorizontalGlue());
            box3.add(logIn);
            box3.add(Box.createHorizontalStrut(20));
            box3.add(cancel);

            username.setPreferredSize(passLabel.getPreferredSize());
            Box mainBox = Box.createVerticalBox();
            mainBox.add(hello);
            mainBox.add(Box.createVerticalStrut(20));
            mainBox.add(box1);
            mainBox.add(Box.createVerticalStrut(20));
            mainBox.add(box2);
            mainBox.add(Box.createVerticalStrut(20));
            mainBox.add(box21);
            mainBox.add(Box.createVerticalStrut(20));
            mainBox.add(box3);

            frame.add(mainBox);
            frame.setContentPane(mainBox);
            frame.setLocationRelativeTo(null);
            frame.pack();

        }
        private void onCancel() {
            frame.setVisible(false);
            dispose();
        }
        private void onOK(LoginView view) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IOException, IllegalAccessException {
            this.login = loginField.getText();
            this.password = passwordField.getText();
           User newUser = new User(UUID.randomUUID(),login,password);
            if(login.length() == 0 || login.length() > 24)
                new ErrorDialog(new JFrame(), ErrorType.WRONG_LOGIN_INPUT);
            else if(password.length() > 24 || password.length() == 0)
                new ErrorDialog(new JFrame(), ErrorType.PASSWORD_LENGTH);
            else if (!password.equals(confirm.getText()))
                new ErrorDialog(new JFrame(),ErrorType.PASSWORD_CONFIRMATION);
            else {
                boolean flag = false;
                for(User us : usersModel.getUsers().values()){
                    if(newUser.getUsername().equals(us.getUsername()))
                        flag = true;
                    else if(newUser.getId().equals(us.getId()))
                        newUser.setId(UUID.randomUUID());
                }
                if (flag)
                    new ErrorDialog(new JFrame(),ErrorType.USERNAME_ALREADY_TAKEN);
                else{
                    usersModel.addUser(newUser);
                    uscon.write();
                    frame.setVisible(false);
                    LoginView newView = new LoginView(uscon,tascon);

                }
            }
        }
    }






