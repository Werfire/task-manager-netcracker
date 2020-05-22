package util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
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
import org.bson.Document;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JsonIO {
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
    public static Map<UUID, MutableTask> readTasksFromDB() throws IOException {
        final String uriString = "mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass%20Community&ssl=false";
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
}