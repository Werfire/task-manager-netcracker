package controllers;

import models.TaskModel;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TaskController implements Serializable {
    private HashMap<Integer, TaskModel> journal = new HashMap<Integer, TaskModel>();
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
        TaskModel curTask = journal.get(id);
        curTask.setDueDate(newDate);

    }
    public void editDescription (Integer id, String des){
        TaskModel curTask = journal.get(id);
        curTask.setDescription(des);
    }
    public void write() throws IOException {
        OutputStream output = new FileOutputStream("database.txt");
        DataOutputStream dataOut = new DataOutputStream(output);
        dataOut.writeUTF(journal.size() + "\n");
        for(Map.Entry<Integer, TaskModel> item : journal.entrySet()){
            dataOut.writeUTF(item.toString() + "\n");
        }
        dataOut.flush();
        output.flush();
        output.close();
    }
    public void read(InputStream input) throws IOException{
        DataInputStream dataIn = new DataInputStream(input);
        int length = dataIn.readInt();
        journal = new HashMap<Integer, TaskModel>(length);
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
    }


}

