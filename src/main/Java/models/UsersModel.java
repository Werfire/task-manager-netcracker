package models;

import interfaces.TasksObservable;
import interfaces.TasksObserver;
import interfaces.UsersObservable;
import interfaces.UsersObserver;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UsersModel implements Serializable, UsersObservable {
    private HashMap<UUID, User> users = new HashMap<>();
    public transient List<UsersObserver> observers = new ArrayList<>();

    public UsersModel() {}

    public UsersModel(UsersModel model) {
        this.users = new HashMap<>(model.users);
    }

    public void addUser(@Nonnull User user) {
        users.put(user.getId(),user);
        notifyObservers();
    }

    public User getUser(UUID id) {
        return users.get(id);
    }
    public HashMap<UUID, User> getJournal() {
        return users;
    }

    public void deleteUser(UUID id) {
        users.remove(id);
        notifyObservers();
    }

    public HashMap<UUID, User> getUsers() {
        return users;
    }

    public int size() {
        return users.size();
    }

    @Override
    public void addObserver(UsersObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(UsersObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(UsersObserver observer : observers)
            observer.update(users);
    }

    @Override
    public boolean equals(Object object) {
        if(this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UsersModel model = (UsersModel) object;
        return this.users.equals(model.users);
    }

}