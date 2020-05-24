import com.sun.net.httpserver.HttpServer;
import controllers.TasksController;
import controllers.UsersController;

import org.glassfish.jersey.server.ResourceConfig;
import views.LoginView;

import javax.swing.*;

import java.io.IOException;
import java.net.URI;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException {

        UsersController usersController = new UsersController();
        TasksController tasksController = new TasksController();
        LoginView loginView = new LoginView(usersController, tasksController);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                tasksController.writeToFile();
            }
        }));

    }
}
