package interfaces;

import models.MutableTask;

import java.util.HashMap;
import java.util.UUID;

public interface TasksObserver {
    void update(HashMap<UUID, MutableTask> journal);
}
