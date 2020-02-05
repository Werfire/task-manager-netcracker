import controllers.TasksController;
import models.MutableTask;
import models.TasksModel;
import util.JsonIO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

public class Tests {

    static TasksController controller = new TasksController();
    static {
        controller.add(new MutableTask(UUID.randomUUID(), "Лаба", "Доделать шестую лабу.",
                LocalDateTime.of(2019, Month.NOVEMBER, 25, 18, 45),
                LocalDateTime.now().plusSeconds(80), UUID.randomUUID(), "In process"));
        controller.add(new MutableTask(UUID.randomUUID(), "Магазин", "Купить молоко и хлеб.",
                LocalDateTime.of(2019, Month.NOVEMBER, 27, 7, 38),
                LocalDateTime.of(2019, Month.NOVEMBER, 27, 18, 0),
                UUID.randomUUID(), "In process"));
        controller.add(new MutableTask(UUID.randomUUID(), "День рождения", "Купить подарок Ивану.",
                LocalDateTime.of(2019, Month.DECEMBER, 1, 14, 5),
                LocalDateTime.of(2019, Month.DECEMBER, 3, 22, 30),
                UUID.randomUUID(), "In process"));
    }

    @Test
    public void testModelSerialization() {
        TasksModel modelBefore = controller.getModel();
        controller.writeToFile();
        controller.readFromFile();
        Assertions.assertEquals(controller.getModel(), modelBefore);
    }

    @Test
    public void testJsonIO() throws IOException {
        TasksModel modelBefore = controller.getModel();
        JsonIO.writeTasks(controller.getModel().getJournal());
        controller.getModel().setJournal(JsonIO.readTasks());
        Assertions.assertEquals(controller.getModel(), modelBefore);
    }

}
