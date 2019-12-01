package controllers;

import models.MutableTask;
import models.Task;
import models.TaskModel;

import javax.annotation.Nonnull;
import java.io.*;
import java.time.LocalDateTime;
import java.util.UUID;

public class TaskController {
    private TaskModel model = new TaskModel();

    public TaskController(){ }

    public TaskController(TaskModel model) {
        this.model = new TaskModel(model);
    }

    public TaskModel getModel() {
        return model;
    }

    public void add (@Nonnull Task newTask){
        model.addTask(newTask);
    }

    public void delete (UUID id) {
            model.deleteTask(id);
    }

    public void editDate (UUID id, LocalDateTime newDate) {
        MutableTask curTask = (MutableTask)model.getTask(id);
        curTask.setDueDate(newDate);
    }

    public void editDescription (UUID id, String des){
        MutableTask curTask = (MutableTask)model.getTask(id);
        curTask.setDescription(des);
    }

    public void write() {
        try( OutputStream output = new FileOutputStream("database.txt")) {
            ObjectOutputStream dataOut = new ObjectOutputStream(output);
            dataOut.writeObject(model);
            dataOut.flush();
            output.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void read() {
        try ( FileInputStream input = new FileInputStream("database.txt")) {
            ObjectInputStream dataIn = new ObjectInputStream(input);
            model = (TaskModel) dataIn.readObject();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            System.err.println("Unsupported class.");
        }


    }


}

