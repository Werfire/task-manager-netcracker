package net;

import controllers.TasksController;
import controllers.UsersController;
import util.JsonIO;

import java.io.IOException;

public class ServerUsersModelManager {

    public static UsersController usersController = null;

    public static UsersController getUsersController() {
        try {
            if(usersController == null)
                usersController = new UsersController();

            usersController.getModel().setMap(JsonIO.readUsersFromDB());
            return usersController;
        }
        catch(IOException e) {
            System.out.println("Error occurred while reading UsersModel data.");
        }

        return usersController;
    }
}
