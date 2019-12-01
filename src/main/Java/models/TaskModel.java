package models;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class TaskModel implements Serializable {
    private HashMap<UUID, Task> journal = new HashMap<>();
    private int lastId = -1;

    public TaskModel() {}

    public TaskModel(TaskModel model) {
        this.journal = new HashMap<>(model.journal);
        this.lastId = model.lastId;
    }

    public void addTask(@Nonnull Task task) {
        journal.put(task.getId(), task);
    }
    public Task getTask(UUID id) {
        return journal.get(id);
    }
    public void deleteTask(UUID id) { journal.remove(id); }
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
