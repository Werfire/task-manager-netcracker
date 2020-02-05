package models;

import interfaces.TasksObservable;
import interfaces.TasksObserver;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.*;

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

    public void setJournal(HashMap<UUID, MutableTask> journal) { this.journal = journal; }

    public HashMap<UUID, MutableTask> getJournal() {
        return journal;
    }

    public int size() {
        return journal.size();
    }

    public static HashMap<UUID, Task> hashMapToImmutableTasks(HashMap<UUID, MutableTask> original) {
        HashMap<UUID, Task> converted = new HashMap<>();
        Collection<MutableTask> values = original.values();
        for(MutableTask task : values)
            converted.put(task.getId(), task);
        return converted;
    }

    public static HashMap<UUID, MutableTask> hashMapToMutableTasks(HashMap<UUID, Task> original) {
        HashMap<UUID, MutableTask> converted = new HashMap<>();
        Collection<Task> values = original.values();
        for(Task task : values)
            converted.put(task.getId(), new MutableTask(task));
        return converted;
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