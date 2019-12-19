package interfaces;

import models.User;

import java.util.HashMap;
import java.util.UUID;

public interface UsersObserver {
    void update(HashMap<UUID, User> users);
}
