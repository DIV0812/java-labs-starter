package edu.course.lab02;          // пакет

import org.junit.jupiter.api.Test;  // @Test

import static org.junit.jupiter.api.Assertions.assertEquals;   // статические импорты
import static org.junit.jupiter.api.Assertions.assertFalse;    // для assertFalse
import static org.junit.jupiter.api.Assertions.assertThrows;   // для assertThrows
import static org.junit.jupiter.api.Assertions.assertTrue;     // для assertTrue

class ProjectTaskTest {

    // Корректная задача: все поля выставлены, статус NEW, isCompleted false
    @Test
    void createsValidTask() {
        ProjectTask task = new ProjectTask("T-1", "Write tests", 8);
        assertEquals("T-1", task.getId());                 // id совпадает
        assertEquals("Write tests", task.getTitle());      // title совпадает
        assertEquals(TaskStatus.NEW, task.getStatus());    // начальный статус NEW
        assertEquals(8, task.getEstimatedHours());         // часы = 8
        assertFalse(task.isCompleted());                   // ещё не завершена
    }

    // Конструктор с null id → исключение
    @Test
    void rejectsNullId() {
        assertThrows(IllegalArgumentException.class,
                () -> new ProjectTask(null, "Title", 1));
    }

    // Конструктор с пустым id (пробелы) → исключение
    @Test
    void rejectsBlankId() {
        assertThrows(IllegalArgumentException.class,
                () -> new ProjectTask("   ", "Title", 1));
    }

    // Конструктор с null title → исключение
    @Test
    void rejectsNullTitle() {
        assertThrows(IllegalArgumentException.class,
                () -> new ProjectTask("T-1", null, 1));
    }

    // Конструктор с пустым title → исключение
    @Test
    void rejectsBlankTitle() {
        assertThrows(IllegalArgumentException.class,
                () -> new ProjectTask("T-1", "", 1));
    }

    // estimatedHours ≤ 0 → исключение (проверяем 0 и −3)
    @Test
    void rejectsNonPositiveEstimatedHours() {
        assertThrows(IllegalArgumentException.class,
                () -> new ProjectTask("T-1", "Title", 0));
        assertThrows(IllegalArgumentException.class,
                () -> new ProjectTask("T-1", "Title", -3));
    }

    // changeStatus меняет статус
    @Test
    void changeStatusUpdatesStatus() {
        ProjectTask task = new ProjectTask("T-1", "Title", 4);
        task.changeStatus(TaskStatus.IN_PROGRESS);          // меняем на IN_PROGRESS
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
    }

    // changeStatus(null) → исключение
    @Test
    void changeStatusRejectsNull() {
        ProjectTask task = new ProjectTask("T-1", "Title", 4);
        assertThrows(IllegalArgumentException.class, () -> task.changeStatus(null));
    }

    // isCompleted: true только для DONE
    @Test
    void isCompletedTrueOnlyForDone() {
        ProjectTask task = new ProjectTask("T-1", "Title", 4);
        assertFalse(task.isCompleted());                    // сразу после создания — false
        task.changeStatus(TaskStatus.DONE);                 // переводим в DONE
        assertTrue(task.isCompleted());                     // теперь — true
    }

    // addHours увеличивает трудоёмкость: 4 + 6 = 10
    @Test
    void addHoursIncreasesEstimatedHours() {
        ProjectTask task = new ProjectTask("T-1", "Title", 4);
        task.addHours(6);                                   // прибавляем 6
        assertEquals(10, task.getEstimatedHours());         // ожидаем 10
    }

    // addHours с не положительным числом → исключение
    @Test
    void addHoursRejectsNonPositive() {
        ProjectTask task = new ProjectTask("T-1", "Title", 4);
        assertThrows(IllegalArgumentException.class, () -> task.addHours(0));   // 0 → искл.
        assertThrows(IllegalArgumentException.class, () -> task.addHours(-2));  // −2 → искл.
    }
}