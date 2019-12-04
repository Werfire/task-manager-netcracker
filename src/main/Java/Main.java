import controllers.TaskController;
import models.Task;
import views.InputError;
import views.InputErrorType;
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

        InputError inputError = new InputError(tasksView, InputErrorType.DATE_FORMAT);

        TaskController controller = new TaskController(tasksView);
        controller.add(new Task(UUID.randomUUID(), "Лаба", "Доделать шестую лабу.",
                LocalDateTime.of(2019, Month.NOVEMBER, 25, 18, 45),
                LocalDateTime.now().plusSeconds(80), 47, 2));
    }
}
