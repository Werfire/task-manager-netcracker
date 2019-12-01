package models;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class TaskModel implements Serializable {
    private static HashMap<UUID, Task> journal = new HashMap<>();
    private ArrayList<UUID> idList = new ArrayList<>();

    public TaskModel() {}

    public TaskModel(TaskModel model) {
        this.journal = new HashMap<>(model.journal);
        this.idList = new ArrayList<>(model.idList);
    }

    public void addTask(@Nonnull Task task) {
        idList.add(task.getId());
        journal.put(task.getId(), task);
    }
    public Task getTask(UUID id) {
        return journal.get(id);
    }
    public void deleteTask(UUID id) {
        idList.remove(id);
        journal.remove(id);
    }
    public HashMap<UUID, Task> getJournal() {
        return journal;
    }
    public UUID getId(int index) {
        return idList.get(index);
    }
    public int size() {
        return journal.size();
    }

    @Override
    public boolean equals(Object object) {
        if(this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TaskModel model = (TaskModel) object;
        return this.journal.equals(model.journal) && this.idList.equals(model.idList);
    }
}
