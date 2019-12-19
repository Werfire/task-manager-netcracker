package views;

import controllers.TasksController;
import models.User;
import models.UsersModel;
import controllers.UsersController;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.UUID;

public class Registration extends JFrame {

        private JTextField passwordField = new JTextField();
        private JTextField loginField = new JTextField();
        private JTextField confirm = new JTextField();
        private JFrame frame = new JFrame("Registration");

        private LoginView loginView;
        private UsersController usersController;
        private TasksController tasksController;

        public Registration(LoginView loginView, UsersController usersController,TasksController tasksController) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {
            super();
            this.loginView = loginView;
            this.usersController = usersController;
            this.tasksController = tasksController;
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            JLabel hello = new JLabel("Input your login and password: " );
            frame.setVisible(true);
            frame.setSize(450, 120);
            frame.setResizable(false);
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    try {
                        onCancel();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (UnsupportedLookAndFeelException ex) {
                        ex.printStackTrace();
                    } catch (InstantiationException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }
            });

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
                        onOK();
                    } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IOException | IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            JButton cancel = new JButton("Cancel");
            cancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    loginView.setVisible(true);
                    try {
                        onCancel();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (UnsupportedLookAndFeelException ex) {
                        ex.printStackTrace();
                    } catch (InstantiationException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }

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
        private void onCancel() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IOException, IllegalAccessException {
            new LoginView(usersController, tasksController);
            frame.setVisible(false);
            dispose();
        }
        private void onOK() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IOException, IllegalAccessException {
            String login = loginField.getText();
            String password = passwordField.getText();
           User newUser = new User(UUID.randomUUID(),login,password);
            if(login.length() < 3 || login.length() > 12)
                new ErrorDialog(new JFrame(), ErrorType.USERNAME_LENGTH);
            else if(password.length() < 4 || password.length() > 18)
                new ErrorDialog(new JFrame(), ErrorType.PASSWORD_LENGTH);
            else if (!password.equals(confirm.getText()))
                new ErrorDialog(new JFrame(),ErrorType.PASSWORD_CONFIRMATION);
            else {
                boolean flag = false;
                for(User us : usersController.getModel().getUsers().values()){
                    if(newUser.getUsername().equals(us.getUsername()))
                        flag = true;
                    else if(newUser.getId().equals(us.getId()))
                        newUser.setId(UUID.randomUUID());
                }
                if (flag)
                    new ErrorDialog(new JFrame(),ErrorType.USERNAME_ALREADY_TAKEN);
                else{
                    usersController.add(newUser);
                    usersController.write();
                    onCancel();
                }
            }
        }
}






