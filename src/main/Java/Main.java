import controllers.TasksController;
import controllers.UsersController;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import views.LoginView;

import javax.swing.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080").path("test");

        Entity<String> entity = Entity.entity("*SomeString*", MediaType.TEXT_PLAIN);

        Invocation.Builder ib = target.request(MediaType.TEXT_PLAIN);


        Response response = ib.get();
        System.out.println(response.readEntity(String.class));
    }
}
