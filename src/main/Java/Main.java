import controllers.TasksController;
import controllers.UsersController;
import views.LoginView;
import views.TasksView;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        UsersController usersController = new UsersController();
        TasksController tasksController = new TasksController();
        //TasksView tasksView = new TasksView(controller);
        LoginView log = new LoginView(usersController, tasksController);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                tasksController.write();
            }
        }));

    }
}
