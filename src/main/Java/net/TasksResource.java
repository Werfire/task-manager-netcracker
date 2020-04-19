package net;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import controllers.TasksController;
import models.MutableTask;
import models.TasksModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.*;

@Path("api")
public class TasksResource {

    TasksController tasksController = ServerTasksModelManager.getTasksController();

    public TasksResource() {
        tasksController.getModel().addObserver(new ServerTasksModelManager());
    }

    @Path("tasks")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addTask(MutableTask task) {
        System.out.println("Post method called.");
        tasksController.add(task);
    }

    @Path("tasks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getTasksJournal() throws IOException {
        System.out.println("Get method called.");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
                TasksModel.hashMapToImmutableTasks(tasksController.getModel().getJournal()));
    }

    @Path("tasks/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateTask(@PathParam("id") UUID id, MutableTask task) {
        System.out.println("Put method called.");
        tasksController.delete(id);
        tasksController.add(task);
    }

    @Path("tasks/{id}")
    @DELETE
    public void deleteTask(@PathParam("id") UUID id) {
        System.out.println("Delete method called.");
        tasksController.delete(id);
    }
}
