package interfaces;

import models.Task;

import java.util.HashMap;

public interface View {
    void render(HashMap<Integer, Task> model);
}
