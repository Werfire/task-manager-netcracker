import controllers.TaskController;
import models.TaskModel;

import java.io.IOException;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException {
        TaskController controller = new TaskController();
        controller.add(new TaskModel(1, "Cleaning", "Clean living room", new Date(), new Date(),
                5, 3));
        controller.write();
    }
}
