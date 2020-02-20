package net;

import models.MutableTask;
import util.JsonIO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Path("api")
public class TasksResource {

    public TasksResource() {
        //System.out.println("TEST");
    }

    @Path("tasks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<UUID, MutableTask> getTasksJournal() throws IOException {
        return JsonIO.readTasks();
    }


    @Path("test")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "Hello there";
    }
}
