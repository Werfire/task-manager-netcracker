package models;

import java.io.Serializable;
import java.util.HashMap;

public class TaskModel implements Serializable {
    private HashMap<Integer, Task> journal = new HashMap<>();
    private int lastId = -1;

    public TaskModel() {}

    public TaskModel(TaskModel model) {
        this.journal = new HashMap<>(model.journal);
        this.lastId = model.lastId;
    }

    public void addTask(Task task) {
        journal.put(++lastId, task);
    }
    public Task getTask(Integer id) {
        return journal.get(id);
    }
    public void deleteTask(Integer id) { journal.remove(id); }
    public int size() {
        return journal.size();
    }

    @Override
    public boolean equals(Object object) {
        if(this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TaskModel model = (TaskModel) object;
        return this.journal.equals(model.journal) && this.lastId == model.lastId;
    }
}
