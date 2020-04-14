package net;

import controllers.TasksController;
import interfaces.TasksObserver;
import models.MutableTask;
import util.JsonIO;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class ServerTasksModelManager implements TasksObserver {

    public static TasksController tasksController = null;

    public static TasksController getTasksController() {
        if(tasksController != null)
            return tasksController;

        try {
            tasksController = new TasksController();
            tasksController.getModel().setJournal(JsonIO.readTasks());
        }
        catch(IOException e) {
            System.out.println("Error occurred while reading TasksModel data.");
        }

        return tasksController;
    }

    @Override
    public void update(HashMap<UUID, MutableTask> journal) {
        try {
            JsonIO.writeTasks(journal);
        }
        catch(IOException e) {
            throw new RuntimeException("Error occurred while saving TasksModel data.", e);
        }
    }
}
