import controllers.TasksController;
import views.TasksView;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        TasksController controller = new TasksController();
        TasksView tasksView = new TasksView(controller);
        tasksView.setVisible(true);
    }
}
