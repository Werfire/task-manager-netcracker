package util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import models.MutableTask;
import models.Task;
import models.TasksModel;
import models.User;
import org.bson.Document;

import java.io.*;
import java.text.ParseException;
import java.util.*;

public class JsonIO {

    final static String uriString = "mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass%20Community&ssl=false";

    public static Map<UUID, MutableTask> readTasks() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        TypeReference<Map<UUID, Task>> typeRef = new TypeReference<Map<UUID, Task>>() {};
        Map<UUID, Task> journal = mapper.readValue(new File("tasks.json"), typeRef);
        return TasksModel.hashMapToMutableTasks(journal);
    }
    public static void writeTasks(HashMap<UUID, MutableTask> journal) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        FileWriter out = new FileWriter("tasks.json");
        out.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(TasksModel.hashMapToImmutableTasks(journal)));
        out.close();
    }

    public static void writeUsers(HashMap<UUID, User> users) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        FileWriter out = new FileWriter("users.json");
        out.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(users));
        out.close();
    }
    public static Map<UUID, User> readUsers() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        TypeReference<Map<UUID, User>> typeRef = new TypeReference<Map<UUID, User>>() {};
        return mapper.readValue(new File("users.json"), typeRef);
    }

    public static Map<UUID, MutableTask> readTasksFromDB() throws IOException {
        MongoClient mongoClient = MongoClients.create(uriString);
        MongoCollection<Document> tasksCollection = mongoClient.getDatabase("taskManager").getCollection("tasks");

        HashMap<UUID, Task> journal = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Block<Document> addBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                try {
                    Task task = mapper.readValue(document.toJson(), Task.class);
                    journal.put(task.getId(), task);
                } catch (JsonProcessingException e) {
                    System.out.println("Error! Unable to parse task fetched from db.");
                }
            }
        };
        FindIterable<Document> findIterable = tasksCollection.find(new Document());
        findIterable.forEach(addBlock);
        mongoClient.close();

        return TasksModel.hashMapToMutableTasks(journal);
    }

    public static void writeTasksToDB(HashMap<UUID, MutableTask> journal) throws IOException {
        MongoClient mongoClient = MongoClients.create(uriString);
        MongoCollection<Document> tasksCollection = mongoClient.getDatabase("taskManager").getCollection("tasks");

        tasksCollection.deleteMany(new Document()); // Cleanup collection

        List<Document> updatedDocuments = new ArrayList<Document>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        JsonNode jsonNode = mapper.readTree(mapper.writeValueAsString(TasksModel.hashMapToImmutableTasks(journal)));
        ArrayNode arrayNode = (ArrayNode) jsonNode;
        for(int i = 0; i < arrayNode.size(); i++) {
            JsonNode curElement = arrayNode.get(i);
            updatedDocuments.add(Document.parse(curElement.toString()));
        }
        tasksCollection.insertMany(updatedDocuments);
        mongoClient.close();
    }

    public static Map<UUID, User> readUsersFromDB() throws IOException {
        MongoClient mongoClient = MongoClients.create(uriString);
        MongoCollection<Document> tasksCollection = mongoClient.getDatabase("taskManager").getCollection("users");

        HashMap<UUID, User> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        Block<Document> addBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                try {
                    User user = mapper.readValue(document.toJson(), User.class);
                    map.put(user.getId(), user);
                } catch (JsonProcessingException e) {
                    System.out.println("Error! Unable to parse user fetched from db.");
                }
            }
        };
        FindIterable<Document> findIterable = tasksCollection.find(new Document());
        findIterable.forEach(addBlock);
        mongoClient.close();

        return map;
    }
}