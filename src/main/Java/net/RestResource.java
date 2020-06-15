package net;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import controllers.TasksController;
import controllers.UsersController;
import models.MutableTask;
import models.TasksModel;
import models.User;
import models.UsersModel;
import org.bson.Document;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.*;

import static com.mongodb.client.model.Filters.eq;


@Path("api")
public class RestResource {

    final static String uriString = "mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass%20Community&ssl=false";

    TasksController tasksController = ServerTasksModelManager.getTasksController();
    UsersController usersController = ServerUsersModelManager.getUsersController();

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
    public Response addTask(MutableTask task) throws IOException {
        System.out.println("Post method called.");
        tasksController.add(task);

        MongoClient mongoClient = MongoClients.create(uriString);
        MongoCollection<Document> tasksCollection = mongoClient.getDatabase("taskManager").getCollection("tasks");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        tasksCollection.insertOne(Document.parse(mapper.writeValueAsString(task)));
        mongoClient.close();

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
    public void updateTask(@PathParam("id") UUID id, MutableTask task) throws IOException {
        System.out.println("Put method called.");
        tasksController.delete(id);
        task.setId(id);
        tasksController.add(task);

        MongoClient mongoClient = MongoClients.create(uriString);
        MongoCollection<Document> tasksCollection = mongoClient.getDatabase("taskManager").getCollection("tasks");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        tasksCollection.updateOne(eq("id", id.toString()), Document.parse(mapper.writeValueAsString(task)));
        mongoClient.close();
    }

    @Path("tasks/{id}")
    @DELETE
    public Response deleteTask(@PathParam("id") UUID id) {
        System.out.println("Delete method called.");
        tasksController.delete(id);

        MongoClient mongoClient = MongoClients.create(uriString);
        MongoCollection<Document> tasksCollection = mongoClient.getDatabase("taskManager").getCollection("tasks");
        tasksCollection.deleteOne(eq("id", id.toString()));
        mongoClient.close();

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

    @Path("users")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user) throws IOException {
        System.out.println("Post method called.");
        usersController.add(user);

        MongoClient mongoClient = MongoClients.create(uriString);
        MongoCollection<Document> usersCollection = mongoClient.getDatabase("taskManager").getCollection("users");
        ObjectMapper mapper = new ObjectMapper();
        usersCollection.insertOne(Document.parse(mapper.writeValueAsString(user)));
        mongoClient.close();

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

    @Path("users")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersMap() throws IOException {
        System.out.println("Get method called.");
        ObjectMapper mapper = new ObjectMapper();
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
                        (usersController.getModel().getMap())))
                .build();
    }

    @Path("users/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateUser(@PathParam("id") UUID id, User user) throws IOException {
        System.out.println("Put method called.");
        usersController.delete(id);
        user.setId(id);
        usersController.add(user);

        MongoClient mongoClient = MongoClients.create(uriString);
        MongoCollection<Document> usersCollection = mongoClient.getDatabase("taskManager").getCollection("users");
        ObjectMapper mapper = new ObjectMapper();
        usersCollection.updateOne(eq("id", id.toString()), Document.parse(mapper.writeValueAsString(user)));
        mongoClient.close();
    }

    @Path("users/{id}")
    @DELETE
    public Response deleteUser(@PathParam("id") UUID id) {
        System.out.println("Delete method called.");
        usersController.delete(id);

        MongoClient mongoClient = MongoClients.create(uriString);
        MongoCollection<Document> tasksCollection = mongoClient.getDatabase("taskManager").getCollection("users");
        tasksCollection.deleteOne(eq("id", id.toString()));
        mongoClient.close();

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
