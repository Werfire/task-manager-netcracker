package interfaces;

public interface UsersObservable {
    void addObserver(UsersObserver observer);
    void removeObserver(UsersObserver observer);
    void notifyObservers();
}
