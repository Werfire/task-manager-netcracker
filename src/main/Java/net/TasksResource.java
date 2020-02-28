package net;

import models.MutableTask;
import util.JsonIO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Path("api")
public class TasksResource {

    // TODO model in RAM

    @Path("tasks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksJournal() throws IOException {
        //return Response.ok(JsonIO.readTasks()).build();
        return Response.ok(Files.readString(FileSystems.getDefault().getPath("tasks.json"),
                StandardCharsets.US_ASCII)).build();
    }

    //@Path("tasks/{id}")
    //@POST
    //@Consumes
}
