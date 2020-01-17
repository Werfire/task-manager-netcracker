package views;

import controllers.TasksController;
import models.User;
import  controllers.UsersController;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class LoginView extends JFrame {
        private JPasswordField passwordField = new JPasswordField();
        private JTextField loginField = new JTextField();
        private JFrame frame;

        private UsersController usersController;
        private TasksController tasksController;

        public LoginView(UsersController usersController, TasksController tasksController) throws ClassNotFoundException,
                UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {
            super();
            this.usersController = usersController;
            this.tasksController = tasksController;
            usersController.mainFrame = this;
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            JLabel hello = new JLabel("Welcome, User!" );
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame = new JFrame("Authorization");
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
                        onLog(tasksController);
                    } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException | IOException ex) {
                        new ErrorDialog((JFrame)getParent(),ErrorType.SOME_SYSTEM_ERROR);
                    }
                }
            });

            JButton exit = new JButton("Exit");

            exit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onExit();
                }
            });
            exit.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    onExit();
                }
            });

            JButton reg = new JButton("Registration");

            reg.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Registration regView = new Registration(LoginView.this, usersController, tasksController);
                        frame.setVisible(false);
                        dispose();
                    } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException | IOException ex) {
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
            frame.setVisible(true);
        }

    private void onExit() {
        dispose();
        System.exit(0);
    }

    private void onLog(TasksController controller) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {
        String login = loginField.getText();
        String password = new String(passwordField.getPassword());
        User user = null;
        if(login.length() > 2 && login.length() <= 12 && password.length() > 3 && password.length() <= 18) {
            for(User u : usersController.getModel().getUsers().values())
                if (login.equals(u.getUsername()) && password.equals(u.getPassword())) {
                    user = u;
                    break;
                }
        }
        else {
            new ErrorDialog(new JFrame(), ErrorType.WRONG_LOGIN_INPUT);
            return;
        }

        if(user == null)
            new ErrorDialog(new JFrame(),ErrorType.WRONG_LOGIN_INPUT);
        else {
            frame.setVisible(false);
            dispose();
            new TasksView(usersController, tasksController, user);
        }
    }
}






