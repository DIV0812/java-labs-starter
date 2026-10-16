package edu.course.lab03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskPrioritizerTest {

    // Замена политики без изменения клиента: работаем с EffortPolicy
    @Test
    void picksHigherByEffort() {
        ProjectTask small = new ProjectTask("T-1", "Small", 2);
        ProjectTask big   = new ProjectTask("T-2", "Big", 20);
        TaskPrioritizer prioritizer = new TaskPrioritizer(new EffortPriorityPolicy());
        assertSame(big, prioritizer.pickHigher(small, big));            // вернётся big
    }

    // Замена политики: тот же клиент, другая стратегия — StatusPolicy
    @Test
    void picksHigherByStatus() {
        ProjectTask newTask = new ProjectTask("T-1", "New", 100);       // статус NEW (4)
        ProjectTask doneTask = new ProjectTask("T-2", "Done", 1);
        doneTask.changeStatus(TaskStatus.DONE);                         // статус DONE (1)

        TaskPrioritizer prioritizer = new TaskPrioritizer(new StatusPriorityPolicy());
        assertSame(newTask, prioritizer.pickHigher(doneTask, newTask)); // вернётся newTask
    }

    // При равенстве приоритетов возвращается первая
    @Test
    void returnsFirstWhenPrioritiesEqual() {
        ProjectTask a = new ProjectTask("T-1", "A", 5);
        ProjectTask b = new ProjectTask("T-2", "B", 5);
        TaskPrioritizer prioritizer = new TaskPrioritizer(new EffortPriorityPolicy());
        assertSame(a, prioritizer.pickHigher(a, b));                    // равны → возвращаем a
    }

    // null-задача → исключение
    @Test
    void rejectsNullTasks() {
        TaskPrioritizer prioritizer = new TaskPrioritizer(new EffortPriorityPolicy());
        ProjectTask task = new ProjectTask("T-1", "Title", 1);
        assertThrows(IllegalArgumentException.class, () -> prioritizer.pickHigher(task, null));
        assertThrows(IllegalArgumentException.class, () -> prioritizer.pickHigher(null, task));
    }

    // null-политика в конструкторе → исключение
    @Test
    void rejectsNullPolicy() {
        assertThrows(IllegalArgumentException.class, () -> new TaskPrioritizer(null));
    }
}