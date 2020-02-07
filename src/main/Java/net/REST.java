package net;

import models.MutableTask;
import util.JsonIO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class REST {

    @Path("tasks.json")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<UUID, MutableTask> getTasksJournal() throws IOException {
        return JsonIO.readTasks();
    }

    @Path("test")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test() throws IOException {
        System.out.println("I'm server");
        return "Hello there";
    }
}
