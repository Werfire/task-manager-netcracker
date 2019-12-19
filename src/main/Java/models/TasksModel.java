package models;

import interfaces.TasksObservable;
import interfaces.TasksObserver;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TasksModel implements Serializable, TasksObservable {
    private HashMap<UUID, MutableTask> journal = new HashMap<>();
    public transient List<TasksObserver> observers = new ArrayList<>();

    public TasksModel() {}

    public TasksModel(TasksModel model) {
        this.journal = new HashMap<>(model.journal);
    }

    public void addTask(@Nonnull MutableTask task) {
        journal.put(task.getId(), task);
        notifyObservers();
    }

    public MutableTask getTask(UUID id) {
        return journal.get(id);
    }

    public void deleteTask(UUID id) {
        journal.remove(id);
        notifyObservers();
    }
    public HashMap<UUID, MutableTask> getJournal() {
        return journal;
    }

    public int size() {
        return journal.size();
    }

    @Override
    public void addObserver(TasksObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(TasksObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(TasksObserver observer : observers)
            observer.update(journal);
    }

    @Override
    public boolean equals(Object object) {
        if(this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TasksModel model = (TasksModel) object;
        return this.journal.equals(model.journal);
    }
}