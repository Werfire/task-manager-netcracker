package views;

import controllers.TasksController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.UUID;

public class Registration extends JFrame {

        private String login;
        private String password;
        private String lastUser;
        private JTextField passwordField = new JTextField();
        private JTextField loginField = new JTextField();
        private JTextField confirm = new JTextField();
        private HashMap<UUID,String> users;

        public Registration(TasksController controller) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
            super();
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            JLabel hello = new JLabel("Input your login and password: " );
            JFrame frame = new JFrame("Registration");
            frame.setVisible(true);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(450, 120);

            frame.setResizable(false);
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
                    onOK(controller);
                }
            });
            JButton cancel = new JButton("Cancel");
            cancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
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
            this.setVisible(false);
            dispose();
        }
        private void onOK(TasksController controller) {
            this.login = loginField.getText();
            this.password = passwordField.getText();
            lastUser = login + " " + password;
            System.out.println("Login: " + login + "\tPassword: " + this.password);
            if(login.length() == 0 || login.length() > 24)
                new ErrorDialog(new JFrame(), ErrorType.WRONG_LOGIN_INPUT);
            else if(password.length() > 24 || password.length() == 0)
                new ErrorDialog(new JFrame(), ErrorType.PASSWORD_LENGTH);
            else if (!password.equals(confirm.getText()))
                new ErrorDialog(new JFrame(),ErrorType.PASSWORD_CONFIRMATION);
            else {
                for(String call : users.values()){
                    if(lastUser.equals(call))
                        new ErrorDialog(new JFrame(),ErrorType.USERNAME_ALREADY_TAKEN);
                    else
                        users.put(UUID.fromString(lastUser),lastUser);
                    TasksView view = new TasksView(controller);
                    view.setVisible(true);
                }
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






