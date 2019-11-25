package controllers;

import models.TaskModel;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TaskController implements Serializable {
    private HashMap journal = new HashMap<Integer, TaskModel>();
    public TaskController(){
    }
    public TaskController(HashMap<Integer, TaskModel> journal) {
        this.journal = journal;
    }
    public void add (TaskModel newTask){
        Object[] arr = journal.keySet().toArray();
        Integer newId;
        if(arr.length > 0)
           newId = (Integer)arr[arr.length - 1] + 1;
        else
            newId = 0;
        journal.put(newId,newTask);
    }
    public void delete (Integer id) {
            journal.remove(id);
    }
    public void editDate (Integer id, Date newDate) {
        TaskModel curTask = (TaskModel) journal.get(id);
//        curTask.setDueDate(newDate);

    }
    public void editDescription (Integer id, String des){
        TaskModel curTask = (TaskModel) journal.get(id);
//        curTask.setDescription(des);
    }
    public void write() throws IOException {
        OutputStream output = new FileOutputStream("database.txt");
        ObjectOutputStream dataOut = new ObjectOutputStream(output);
        dataOut.writeObject(journal);
        dataOut.flush();
        output.flush();
        output.close();
    }
    public void read() throws IOException, ClassNotFoundException {
        FileInputStream input = new FileInputStream("database.txt");
        ObjectInputStream dataIn = new ObjectInputStream(input);
        journal = (HashMap) dataIn.readObject();
    }


}

