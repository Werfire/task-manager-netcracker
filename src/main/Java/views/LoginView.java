package views;

import controllers.TasksController;
import models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class LoginView extends JFrame {
        private String login;
        private String password;

        private JPasswordField passwordField = new JPasswordField();
        private JTextField loginField = new JTextField();

        private HashMap<UUID,String> usersModel;

        public LoginView(TasksController controller) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
            super();
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            JLabel hello = new JLabel("Welcome, User!" );
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JFrame frame = new JFrame("Authorization");
            frame.setVisible(true);
            frame.setSize(450, 120);

            frame.setResizable(false);
            Box box1 = Box.createHorizontalBox();
            JLabel loginLabel = new JLabel("Login:");
            loginField = new JTextField(24);
            box1.add(loginLabel);
            box1.add(Box.createHorizontalStrut(20));
            box1.add(loginField);

            Box box2 = Box.createHorizontalBox();
            JLabel passLabel = new JLabel("Password:");
            passwordField = new JPasswordField(24);
            box2.add(passLabel);
            box2.add(Box.createHorizontalStrut(20));
            box2.add(passwordField);

            Box box3 = Box.createHorizontalBox();
            JButton logIn = new JButton("Log in");
            logIn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        onLog(controller);
                    } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException ex) {
                        new ErrorDialog((JFrame)getParent(),ErrorType.SOME_SYSTEM_ERROR);
                    }
                }
            });
            JButton exit = new JButton("Exit");
            exit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onCancel();
                }
            });
            JButton reg = new JButton("Registration");
            reg.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Registration regView = new Registration(controller);
                        setEnabled(false);
                    } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException ex) {
                        new ErrorDialog((JFrame)getParent(),ErrorType.SOME_SYSTEM_ERROR);                    }
                }
            });
            box3.add(Box.createHorizontalGlue());
            box3.add(reg);
            box3.add(Box.createHorizontalGlue());
            box3.add(logIn);
            box3.add(Box.createHorizontalStrut(20));
            box3.add(exit);

            loginLabel.setPreferredSize(passLabel.getPreferredSize());
            Box mainBox = Box.createVerticalBox();
            mainBox.add(hello);
            mainBox.add(Box.createVerticalStrut(20));
            mainBox.add(box1);
            mainBox.add(Box.createVerticalStrut(20));
            mainBox.add(box2);
            mainBox.add(Box.createVerticalStrut(20));
            mainBox.add(box3);

            frame.add(mainBox);
            frame.setContentPane(mainBox);
            frame.setLocationRelativeTo(null);
            frame.pack();



        }

    private void onCancel() {
        dispose();
        System.exit(0);
    }
    private void onLog(TasksController controller) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        LoginView.this.login = loginField.getText();
        LoginView.this.password = new String(passwordField.getPassword());
        System.out.println("Login: " + LoginView.this.login + "\tPassword: " + LoginView.this.password);
        if(login.length() == 0 || login.length() > 24)
            new ErrorDialog((JFrame)getParent(), ErrorType.WRONG_LOGIN_INPUT);
        else if(password.length() > 24 || password.length() == 0)
            new ErrorDialog((JFrame)getParent(), ErrorType.PASSWORD_LENGTH);
        else {
                TasksView view = new TasksView(controller, new User(UUID.randomUUID(), "User", "qwerty"));
                view.setVisible(true);
        }
    }
//    public String[] read(){
//        try (FileInputStream input = new FileInputStream("database.txt")) {
//            ObjectInputStream dataIn = new ObjectInputStream(input);
//
////            users.values() = (Collection<String>) dataIn.readObject();
//
////            } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

}




