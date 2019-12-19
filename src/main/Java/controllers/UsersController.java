package controllers;

import models.TasksModel;
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

    @Nonnull
    public UsersModel getModel() {
        return model;
    }

    @Nonnull
    public JFrame getMainFrame() {
        return mainFrame;
    }

    public void read() throws IOException, ClassNotFoundException {
        FileInputStream in = new FileInputStream("C:\\Users\\dmitry\\Desktop\\MANAGER\\task-manager\\users.txt");
        try {
        ObjectInputStream input = new ObjectInputStream(in);
        model = new UsersModel((UsersModel) input.readObject());
        input.close();
        }
        catch (EOFException e){
            new ErrorDialog(new JFrame(), ErrorType.SOME_SYSTEM_ERROR);
            model = new UsersModel();
        }
        catch (IOException e){
            new ErrorDialog(new JFrame(),ErrorType.IO_EXCEPTION);
        }
        in.close();
    }
    public void write() throws IOException {
        FileOutputStream out = new FileOutputStream("C:\\Users\\dmitry\\Desktop\\MANAGER\\task-manager\\users.txt");
        ObjectOutputStream output = new ObjectOutputStream(out);
        output.writeObject(model);
        out.close();
    }
}
