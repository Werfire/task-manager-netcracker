package interfaces;

import models.Task;

import java.util.HashMap;

public interface TasksObservable {
    void addObserver(TasksObserver observer);
    void removeObserver(TasksObserver observer);
    void notifyObservers();
}
