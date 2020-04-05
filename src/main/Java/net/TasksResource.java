package net;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import controllers.TasksController;
import models.MutableTask;
import models.TasksModel;
import util.JsonIO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.UUID;

@Path("api")
public class TasksResource {

    TasksController tasksController = ServerTasksModelManager.getTasksController();

    public TasksResource() {
        tasksController.getModel().addObserver(new ServerTasksModelManager());
    }

    @Path("tasks")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addTask(MutableTask task) {
        System.out.println("Post method called.");
        tasksController.add(task);
        return Response
                .status(201)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .entity("Task added.")
                .build();
    }

    @Path("tasks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksJournal() throws IOException {
        System.out.println("Get method called.");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String response = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
                TasksModel.hashMapToImmutableTasks(tasksController.getModel().getJournal()));
        return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
//                .entity(Files.readString(FileSystems.getDefault().getPath("tasks.json"),
//                        StandardCharsets.US_ASCII))
                .entity(response)
                .build();
//                .ok(Files.readString(FileSystems.getDefault().getPath("tasks.json"),
//                StandardCharsets.US_ASCII)).build();
    }

    @Path("tasks/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateTask(@PathParam("id") UUID id, MutableTask task) {
        System.out.println("Put method called.");
        tasksController.delete(id);
        tasksController.add(task);
        return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .entity("Task updated.")
                .build();
    }

    @Path("tasks/{id}")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
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
                .entity("Task deleted.")
                .build();
    }
}
