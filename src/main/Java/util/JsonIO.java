package util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import models.MutableTask;
import models.Task;
import models.TasksModel;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class JsonIO {
    public static HashMap<UUID, MutableTask> readTasks() throws FileNotFoundException, JsonProcessingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        TypeReference<HashMap<UUID, Task>> typeRef = new TypeReference<>() {};
        HashMap<UUID, Task> journal = mapper.readValue(new File("tasks.json"), typeRef);
        return TasksModel.hashMapToMutableTasks(journal);
    }
    public static void writeTasks(HashMap<UUID, MutableTask> journal) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        FileWriter out = new FileWriter("tasks.json");
        out.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(TasksModel.hashMapToImmutableTasks(journal)));
        out.close();
    }

}