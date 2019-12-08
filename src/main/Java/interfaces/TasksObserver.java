package interfaces;

import models.Task;

import java.util.HashMap;
import java.util.UUID;

public interface TasksObserver {
    void update(HashMap<UUID, Task> journal);
}
