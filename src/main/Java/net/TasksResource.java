package net;

import controllers.TasksController;
import models.MutableTask;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    public HashMap<UUID, MutableTask> getTasksJournal() {
        return tasksController.getModel().getJournal();
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
