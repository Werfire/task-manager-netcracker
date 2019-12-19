package controllers;

import interfaces.TasksObserver;
import models.MutableTask;
import models.Task;
import models.TasksModel;
import util.NotificationsScheduler;
import views.ErrorDialog;
import views.ErrorType;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class TasksController {
    @Nonnull private TasksModel model;
    @Nonnull public JFrame mainFrame = new JFrame();

    public TasksController() {
        this.model = new TasksModel();
    }

    public TasksController(@Nonnull TasksModel model) {
        this.model = new TasksModel(model);
    }

    @Nonnull
    public TasksModel getModel() {
        return model;
    }

    public void add (@Nonnull MutableTask newTask){
        model.addTask(newTask);
    }

    @Nonnull
    public MutableTask get(UUID id) {
        return model.getTask(id);
    }

    public void delete (UUID id) {
        model.deleteTask(id);
    }

    public void editName(UUID id, String name) {
        model.getTask(id).setName(name);
        model.notifyObservers();
    }

    public void editDueDate(UUID id, LocalDateTime newDate) {
        model.getTask(id).setDueDate(newDate);
        model.notifyObservers();
    }

    public void editDescription (UUID id, String des){
        model.getTask(id).setDescription(des);
        model.notifyObservers();
    }

    public void changeStatus(UUID id, int statusId) {
        model.getTask(id).setStatusId(statusId);
        model.notifyObservers();
    }

    public void write() {
        try(OutputStream output = new FileOutputStream("tasks.txt")) {
            ObjectOutputStream dataOut = new ObjectOutputStream(output);
            dataOut.writeObject(model);
            dataOut.flush();
            output.flush();
        }
        catch (IOException e){
            new ErrorDialog(mainFrame, ErrorType.IO_EXCEPTION);
        }
    }

    public TasksModel read() {
        try (FileInputStream input = new FileInputStream("tasks.txt")) {
            ObjectInputStream dataIn = new ObjectInputStream(input);
            List<TasksObserver> observers = model.observers;
            model = (TasksModel) dataIn.readObject();
            model.observers = observers;
            model.notifyObservers();
        }
        catch (IOException e){
            new ErrorDialog(mainFrame, ErrorType.IO_EXCEPTION);
        }
        catch (ClassNotFoundException e){
            new ErrorDialog(mainFrame, ErrorType.CLASS_NOT_FOUND_EXCEPTION);
        }
        return model;

    }
}
