package controllers;

import models.User;
import models.UsersModel;
import views.ErrorDialog;
import util.ErrorType;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.io.*;
import java.util.UUID;

public class UsersController implements Serializable {
    @Nonnull private UsersModel model;
    @Nonnull public JFrame mainFrame = new JFrame();

    public UsersController() {
        model = new UsersModel();
    }

    public UsersController(@Nonnull UsersModel model) {
        this.model = model;
    }

    public void add(User user) {
        model.addUser(user);
    }

    public User get(UUID id) { return model.getUser(id); }

    public void delete (UUID id) {
        model.deleteUser(id);
    }

    @Nonnull
    public UsersModel getModel() {
        return model;
    }

    @Nonnull
    public JFrame getMainFrame() {
        return mainFrame;
    }

    public void readFromFile() throws IOException, ClassNotFoundException {
        FileInputStream in = new FileInputStream("usersByte");
        try {
            ObjectInputStream input = new ObjectInputStream(in);
            model = new UsersModel((UsersModel) input.readObject());
            input.close();
        }
        catch (EOFException e){
            model = new UsersModel();
            writeToFile();
        }
        catch (IOException e){
            new ErrorDialog(mainFrame,ErrorType.IO_EXCEPTION);
        }
        in.close();
    }
    public void writeToFile() throws IOException {
        FileOutputStream out = new FileOutputStream("usersByte");
        ObjectOutputStream output = new ObjectOutputStream(out);
        output.writeObject(model);
        out.close();
    }
}