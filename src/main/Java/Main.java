import controllers.TaskController;
import models.Task;
import views.TasksView;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        TasksView tasksView = new TasksView();
        tasksView.setVisible(true);

        TaskController controller = new TaskController(tasksView);
        controller.add(new Task(UUID.randomUUID(), "Лаба", "Доделать шестую лабу.",
                LocalDateTime.of(2019, Month.NOVEMBER, 25, 18, 45),
                LocalDateTime.now().plusSeconds(70), 47, 2));
    }
}