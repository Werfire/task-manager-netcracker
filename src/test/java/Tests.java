import controllers.TasksController;
import models.MutableTask;
import models.Task;
import models.TasksModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

public class Tests {

    @Test
    public void testModelSerialization() {
        TasksController controller = new TasksController();
        controller.add(new MutableTask(UUID.randomUUID(), "Лаба", "Доделать шестую лабу.",
                LocalDateTime.of(2019, Month.NOVEMBER, 25, 18, 45),
                LocalDateTime.now().plusSeconds(80), UUID.randomUUID(), 0));
        controller.add(new MutableTask(UUID.randomUUID(), "Магазин", "Купить молоко и хлеб.",
                LocalDateTime.of(2019, Month.NOVEMBER, 27, 7, 38),
                LocalDateTime.of(2019, Month.NOVEMBER, 27, 18, 0),
                UUID.randomUUID(), 0));
        controller.add(new MutableTask(UUID.randomUUID(), "День рождения", "Купить подарок Ивану.",
                LocalDateTime.of(2019, Month.DECEMBER, 1, 14, 5),
                LocalDateTime.of(2019, Month.DECEMBER, 3, 22, 30),
                UUID.randomUUID(), 0));
        controller.write();
        TasksModel modelBefore = controller.getModel();
        controller.read();
        Assertions.assertEquals(controller.getModel(), modelBefore);
    }

}
