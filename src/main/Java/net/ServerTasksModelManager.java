package net;

import controllers.TasksController;
import interfaces.TasksObserver;
import models.MutableTask;
import util.JsonIO;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class ServerTasksModelManager {

    public static TasksController tasksController = null;

    public static TasksController getTasksController() {
        try {
            if(tasksController == null)
                tasksController = new TasksController();

            tasksController.getModel().setJournal(JsonIO.readTasksFromDB());
            //tasksController.getModel().setJournal(JsonIO.readTasks());
            return tasksController;
        }
        catch(IOException e) {
            System.out.println("Error occurred while reading TasksModel data.");
        }

        return tasksController;
    }
}
