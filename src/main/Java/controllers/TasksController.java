package controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import interfaces.TasksObserver;
import models.MutableTask;
import models.Task;
import models.TasksModel;
import views.ErrorDialog;
import util.ErrorType;

import javax.annotation.Nonnull;
import javax.json.Json;
import javax.swing.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
//        System.out.println("add");
        model.addTask(newTask);
    }
    public void create(){
        model.addTask(new MutableTask());
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

    public void changeStatus(UUID id, String statusId) {
        model.getTask(id).setStatusId(statusId);
        model.notifyObservers();
    }

    public void writeToFile() {
        try(OutputStream output = new FileOutputStream("tasksByte")) {
            ObjectOutputStream dataOut = new ObjectOutputStream(output);
            dataOut.writeObject(model);
            dataOut.flush();
            output.flush();
        }
        catch (IOException e){
            new ErrorDialog(mainFrame, ErrorType.IO_EXCEPTION);
        }
    }

    public void readFromFile() {
        try (FileInputStream input = new FileInputStream("tasksByte")) {
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
    }

    public void updateJournalFromServer() {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080/rest").path("api/tasks");
        Response response = webTarget.request(MediaType.APPLICATION_JSON).get();

        try {
            String json = response.readEntity(String.class);
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            TypeReference<Map<UUID, Task>> typeRef = new TypeReference<Map<UUID, Task>>(){};
            model.setJournal(TasksModel.hashMapToMutableTasks(mapper.readValue(json, typeRef)));
        }
        catch(IOException ex) {
            new ErrorDialog(mainFrame, ErrorType.IO_EXCEPTION);
        }
    }
}
