package interfaces;

import models.TaskModel;

import java.util.HashMap;

public interface View {
    void render(HashMap<Integer, TaskModel> model);
}
