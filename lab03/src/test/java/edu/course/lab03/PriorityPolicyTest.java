package edu.course.lab03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PriorityPolicyTest {

    // EffortPriorityPolicy: приоритет = число часов
    @Test
    void effortPriorityReturnsHours() {
        ProjectTask task = new ProjectTask("T-1", "Do work", 8);       // 8 часов
        PriorityPolicy policy = new EffortPriorityPolicy();            // ссылка интерфейсного типа
        assertEquals(8, policy.calculatePriority(task));               // приоритет = 8
    }

    // EffortPriorityPolicy: больше часов — выше приоритет
    @Test
    void effortPriorityHigherForMoreHours() {
        ProjectTask small = new ProjectTask("T-1", "Small", 2);
        ProjectTask big   = new ProjectTask("T-2", "Big", 20);
        PriorityPolicy policy = new EffortPriorityPolicy();
        assertEquals(true, policy.calculatePriority(big) > policy.calculatePriority(small));
    }

    // EffortPriorityPolicy: null → исключение
    @Test
    void effortPriorityRejectsNull() {
        PriorityPolicy policy = new EffortPriorityPolicy();
        assertThrows(IllegalArgumentException.class, () -> policy.calculatePriority(null));
    }

    // StatusPriorityPolicy: NEW — самый высокий (4)
    @Test
    void statusPriorityNewIsHighest() {
        ProjectTask task = new ProjectTask("T-1", "Title", 1);         // начальный статус NEW
        PriorityPolicy policy = new StatusPriorityPolicy();
        assertEquals(4, policy.calculatePriority(task));
    }

    // StatusPriorityPolicy: DONE — самый низкий (1)
    @Test
    void statusPriorityDoneIsLowest() {
        ProjectTask task = new ProjectTask("T-1", "Title", 1);
        task.changeStatus(TaskStatus.DONE);                             // меняем статус
        PriorityPolicy policy = new StatusPriorityPolicy();
        assertEquals(1, policy.calculatePriority(task));
    }

    // StatusPriorityPolicy: null → исключение
    @Test
    void statusPriorityRejectsNull() {
        PriorityPolicy policy = new StatusPriorityPolicy();
        assertThrows(IllegalArgumentException.class, () -> policy.calculatePriority(null));
    }
}