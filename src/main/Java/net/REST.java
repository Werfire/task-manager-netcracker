package net;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import models.Task;
import models.TasksModel;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class REST {
    public static void readTasks() throws FileNotFoundException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TasksModel journal = new TasksModel();
        journal = mapper.readValue("tasks.json",TasksModel.class);
    }
    public static void writeTasks(TasksModel journal) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        FileOutputStream out = new FileOutputStream("tasks.json");
        out.write(mapper.writeValueAsBytes(journal.getJournal()));
        out.close();
    }


}