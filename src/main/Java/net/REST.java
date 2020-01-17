package net;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import models.Task;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class REST {
    public static <JSONObject> ArrayList<Task> readTasks(){
        JSONObject obj;
        ArrayList<Task> answer = new ArrayList<>();
        Task curTask;
        String fileName = "tasks.json ";
        String line = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
//                obj = (JSONObject) new JsonParser().parse(line);
                ObjectMapper mapper = new ObjectMapper();
                obj = (JSONObject) mapper.readValue(line, Task.class);
                answer.add((Task)obj);
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
//            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
//            System.out.println("Error reading file '" + fileName + "'");
        }
        return answer;
    }

}