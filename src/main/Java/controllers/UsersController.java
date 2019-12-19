package controllers;

import models.TasksModel;
import models.User;
import models.UsersModel;
import org.checkerframework.checker.nullness.qual.NonNull;
import views.ErrorDialog;
import views.ErrorType;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.io.*;

public class UsersController implements Serializable {
    @Nonnull private UsersModel model;
    @Nonnull public JFrame mainFrame = new JFrame();

    public UsersController() throws IOException, ClassNotFoundException {
        model = new UsersModel();
        read();
    }

    public UsersController(@Nonnull UsersModel model) {
        this.model = model;
    }

    public void add(User user) {
        model.addUser(user);
    }

    @Nonnull
    public UsersModel getModel() {
        return model;
    }

    @Nonnull
    public JFrame getMainFrame() {
        return mainFrame;
    }

    public void read() throws IOException, ClassNotFoundException {
        FileInputStream in = new FileInputStream("users.txt");
        try {
            ObjectInputStream input = new ObjectInputStream(in);
            model = new UsersModel((UsersModel) input.readObject());
            input.close();
        }
        catch (EOFException e){
            model = new UsersModel();
            write();
        }
        catch (IOException e){
            new ErrorDialog(mainFrame,ErrorType.IO_EXCEPTION);
        }
        in.close();
    }
    public void write() throws IOException {
        FileOutputStream out = new FileOutputStream("users.txt");
        ObjectOutputStream output = new ObjectOutputStream(out);
        output.writeObject(model);
        out.close();
    }
}