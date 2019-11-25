package controllers;

import models.MutableTask;
import models.Task;
import models.TaskModel;

import java.io.*;
import java.util.Date;

public class TaskController {
    private TaskModel model = new TaskModel();
    public TaskController(){
    }
    public TaskController(TaskModel model) {
        this.model = new TaskModel(model);
    }
    public void add (Task newTask){
        model.addTask(newTask);
    }
    public void delete (Integer id) {
            model.deleteTask(id);
    }
    public void editDate (Integer id, Date newDate) {
        MutableTask curTask = (MutableTask)model.getTask(id);
        curTask.setDueDate(newDate);
    }
    public void editDescription (Integer id, String des){
        MutableTask curTask = (MutableTask)model.getTask(id);
        curTask.setDescription(des);
    }
    public void write() throws IOException {
        OutputStream output = new FileOutputStream("database.txt");
        DataOutputStream dataOut = new DataOutputStream(output);
        dataOut.writeUTF(model.size() + "\n");
        for(int i = 0; i < model.size(); i++){
            dataOut.writeUTF(model.getTask(i).toString() + "\n");
        }
        dataOut.flush();
        output.flush();
        output.close();
    }
    /*public void read(InputStream input) throws IOException{
        DataInputStream dataIn = new DataInputStream(input);
        int length = dataIn.readInt();
        model = new HashMap<>(length);
        for (int i = 0; i < length ; i++) {
            int id = dataIn.readInt();
            String name = dataIn.readUTF();
            String description = dataIn.readUTF();
//            Date creationDate = dataIn.readUTF();// TODO HOW DO THIS???
//            Date dueDate = dataIn.readUTF();
            int authorId = dataIn.readInt();
            int statusId = dataIn.readInt();
//            TaskModel curTask = new TaskModel(id,name,description,);//TODO complete
//            add(curTask);
        }
    }*/


}

