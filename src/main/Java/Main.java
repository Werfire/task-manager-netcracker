import controllers.TasksController;
import controllers.UsersController;
import net.TestHandler;
import org.eclipse.jetty.server.Server;

import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.webapp.WebAppContext;
import views.LoginView;

import javax.swing.*;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UsersController usersController = new UsersController();
        TasksController tasksController = new TasksController();
        LoginView loginView = new LoginView(usersController, tasksController);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                tasksController.writeToFile();
            }
        }));

        try {
            Server server = new Server(8080);

//            WebAppContext webapp = new WebAppContext();
//            webapp.setContextPath("/task-manager");
//            webapp.setWar(new File("target/task-manager-1.0.war").getAbsolutePath());
//            server.setHandler(webapp);

            ServletHandler handler = new ServletHandler();
            handler.addServletWithMapping(TestHandler.class, "/hello");
            server.setHandler(handler);

            server.start();
            server.join();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }
}
