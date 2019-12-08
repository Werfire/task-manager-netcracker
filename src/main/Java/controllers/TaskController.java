package controllers;

import models.MutableTask;
import models.Task;
import models.TaskModel;
import views.Notification;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.Timer;
import views.InputError;
import views.InputErrorType;
public class TaskController {
    @Nonnull private TaskModel model = new TaskModel();
    @Nonnull ArrayList<Timer> timers = new ArrayList<>();
    @Nonnull JFrame frame;

    public TaskController(@Nonnull JFrame frame){
        this.frame = frame;
    }

    public TaskController(@Nonnull JFrame frame, @Nonnull TaskModel model) {
        this.frame = frame;
        this.model = new TaskModel(model);
        for(HashMap.Entry<UUID, Task> entry : model.getJournal().entrySet()) {
            scheduleNotifications(entry.getValue());
        }
    }

    public TaskModel getModel() {
        return model;
    }

    public void add (@Nonnull Task newTask){
        model.addTask(newTask);
        scheduleNotifications(newTask);
    }

    public UUID getId(int index) {
        return model.getId(index);
    }

    public Task get(UUID id) {
        return model.getTask(id);
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
        try(OutputStream output = new FileOutputStream("database.txt")) {
            ObjectOutputStream dataOut = new ObjectOutputStream(output);
            dataOut.writeObject(model);
            dataOut.flush();
            output.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public TaskModel read() {
        try (FileInputStream input = new FileInputStream("C:\\Users\\dmitry\\Desktop\\MANAGER\\task-manager\\database.txt")) {
            ObjectInputStream dataIn = new ObjectInputStream(input);
            model = (TaskModel) dataIn.readObject();

        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            System.err.println("Unsupported class.");
        }
        return model;
    }

    private void scheduleNotifications(Task task) {
        if(LocalDateTime.now().compareTo(task.getDueDate().minusMinutes(1)) < 0) {
            Timer timer = new Timer();
            timers.add(timer);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    new Notification(frame, task, true);
                    timers.remove(timer);
                }
            }, Date.from(task.getDueDate().minusMinutes(1)
                    .atZone(ZoneId.systemDefault())
                    .toInstant()));
        }

        if(LocalDateTime.now().compareTo(task.getDueDate()) < 0) {
            Timer timer = new Timer();
            timers.add(timer);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    new Notification(frame, task, false);
                    timers.remove(timer);
                }
            }, Date.from(task.getDueDate()
                    .atZone(ZoneId.systemDefault())
                    .toInstant()));
        }
    }
}
