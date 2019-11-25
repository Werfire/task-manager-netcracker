package controllers;

import models.Task;
import models.TaskModel;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TaskController implements Serializable {
    private TaskModel model = new TaskModel();
    public TaskController(){
    }
    public TaskController(TaskModel model) {
        this.model = new TaskModel(model);
    }
    public void add (Task newTask){
        model.addTask(newTask);
    }
    public void delete (Integer id) {
            model.deleteTask(id);
    }
    public void editDate (Integer id, Date newDate) {
        Task curTask = model.getTask(id);
        curTask.setDueDate(newDate);

    }
    public void editDescription (Integer id, String des){
        Task curTask = model.getTask(id);
        curTask.setDescription(des);
    }
    public void write() throws IOException {
        OutputStream output = new FileOutputStream("database.txt");
        ObjectOutputStream dataOut = new ObjectOutputStream(output);
        dataOut.writeObject(model);
        dataOut.flush();
        output.flush();
        output.close();
    }
    public void read() throws IOException, ClassNotFoundException {
        FileInputStream input = new FileInputStream("database.txt");
        ObjectInputStream dataIn = new ObjectInputStream(input);
        model = (TaskModel) dataIn.readObject();
    }


}

