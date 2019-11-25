package controllers;

import models.MutableTask;
import models.Task;
import models.TaskModel;

import java.io.*;
import java.time.LocalDateTime;

public class TaskController {
    private TaskModel model = new TaskModel();

    public TaskController(){}

    public TaskController(TaskModel model) {
        this.model = new TaskModel(model);
    }

    public TaskModel getModel() {
        return model;
    }

    public void add (Task newTask){
        model.addTask(newTask);
    }

    public void delete (Integer id) {
            model.deleteTask(id);
    }

    public void editDate (Integer id, LocalDateTime newDate) {
        MutableTask curTask = (MutableTask)model.getTask(id);
        curTask.setDueDate(newDate);
    }

    public void editDescription (Integer id, String des){
        MutableTask curTask = (MutableTask)model.getTask(id);
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

