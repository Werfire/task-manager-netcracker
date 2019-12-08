import controllers.TaskController;
import models.Task;
import models.TaskModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Set;
import java.util.UUID;

public class Tests {

    @Test
    public void testModelSerialization() {
        TaskController controller = new TaskController(new JFrame());
        controller.add(new Task(UUID.randomUUID(), "Лаба", "Доделать шестую лабу.",
                LocalDateTime.of(2019, Month.NOVEMBER, 25, 18, 45),
                LocalDateTime.of(2019, Month.DECEMBER, 16, 10, 0), UUID.randomUUID(), 2));
        controller.add(new Task(UUID.randomUUID(), "Магазин", "Купить молоко и хлеб.",
                LocalDateTime.of(2019, Month.NOVEMBER, 27, 7, 38),
                LocalDateTime.of(2019, Month.NOVEMBER, 27, 18, 0), UUID.randomUUID(), 6));
        controller.add(new Task(UUID.randomUUID(), "День рождения", "Купить подарок Ивану.",
                LocalDateTime.of(2019, Month.DECEMBER, 1, 14, 5),
                LocalDateTime.of(2019, Month.DECEMBER, 3, 22, 30), UUID.randomUUID(), 14));
        controller.write();
        TaskModel modelBefore = controller.getModel();
        controller.read();
        Assertions.assertEquals(controller.getModel(), modelBefore);
    }

}
