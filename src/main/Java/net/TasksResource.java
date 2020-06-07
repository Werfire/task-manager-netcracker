package net;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import controllers.TasksController;
import controllers.UsersController;
import models.MutableTask;
import models.TasksModel;
import models.User;
import models.UsersModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.*;



@Path("api")
public class TasksResource {

    TasksController tasksController = ServerTasksModelManager.getTasksController();

    public TasksResource() {
        tasksController.getModel().addObserver(new ServerTasksModelManager());
    }

    @Path("test")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response testMethod(Integer a) {
        System.out.println(a);
        return Response.status(200).build();
    }

    @Path("tasks")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTask(MutableTask task) {
        System.out.println("Post method called.");

        tasksController.add(task);
        return Response
                .status(Response.Status.CREATED.getStatusCode())
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, Content-Type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .entity("")
                .build();
    }
    @GET
    @Path("users")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMethod(){
        return "This API needs login.";
    }

    @POST
    @Path("users")
    public void postMethod(String username, String password){
        User user = new User(username,password);

    }

    @Path("tasks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksJournal() throws IOException {
        System.out.println("Get method called.");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        System.out.println("SECURED API");
        return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .entity(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
                        TasksModel.hashMapToImmutableTasks(tasksController.getModel().getJournal())))
                .build();

    }

    @Path("tasks/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateTask(@PathParam("id") UUID id, MutableTask task) {
        System.out.println("Put method called.");
        tasksController.delete(id);
        task.setId(id);
        tasksController.add(task);
    }

    @Path("tasks/{id}")
    @DELETE
    public Response deleteTask(@PathParam("id") UUID id) {
        System.out.println("Delete method called.");
        tasksController.delete(id);
        return Response
            .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
//                .entity("")
                .build();
    }
}
