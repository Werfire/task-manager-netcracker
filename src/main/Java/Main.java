import controllers.TasksController;
import controllers.UsersController;
import models.MutableTask;
import net.REST;
import net.TasksApplication;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.server.model.Resource;
import views.LoginView;

import javax.swing.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

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



        Client client = ClientBuilder.newClient(new ClientConfig().register(REST.class));
        WebTarget webTarget = client.target("http://localhost:8080").path("test");

        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.TEXT_PLAIN);
        Response response = invocationBuilder.get();

        String string = response.readEntity(String.class);
        System.out.println(string);
        //for(MutableTask task : journal.values())
        //    System.out.println(task.toString());

    }
}
