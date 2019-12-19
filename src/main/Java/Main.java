import controllers.TasksController;
import views.LoginView;
import views.TasksView;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        TasksController controller = new TasksController();
        TasksView tasksView = new TasksView(controller);
        tasksView.setVisible(true);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                controller.write();
            }
        }));

//        LoginView log = new LoginView(controller);

    }
}
