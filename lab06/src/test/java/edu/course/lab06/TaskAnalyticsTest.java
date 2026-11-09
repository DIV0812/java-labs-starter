package edu.course.lab06;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TaskAnalyticsTest {

    // Утилита для сборки списка
    private List<ProjectTask> sampleTasks() {
        List<ProjectTask> tasks = new ArrayList<>();
        tasks.add(new ProjectTask("T-3", "C", "backend",  8, TaskStatus.READY));
        tasks.add(new ProjectTask("T-1", "A", "frontend", 4, TaskStatus.IN_PROGRESS));
        tasks.add(new ProjectTask("T-2", "B", "backend",  8, TaskStatus.READY));
        tasks.add(new ProjectTask("T-4", "D", "backend",  2, TaskStatus.DONE));
        return tasks;
    }

    // Число задач по статусам
    @Test
    void countByStatus() {
        Map<TaskStatus, Integer> counts = new TaskAnalytics().countByStatus(sampleTasks());
        assertEquals(2, counts.get(TaskStatus.READY));        // T-3, T-2
        assertEquals(1, counts.get(TaskStatus.IN_PROGRESS));  // T-1
        assertEquals(1, counts.get(TaskStatus.DONE));         // T-4
    }

    // Уникальные категории
    @Test
    void uniqueCategories() {
        Set<String> categories = new TaskAnalytics().uniqueCategories(sampleTasks());
        assertEquals(Set.of("backend", "frontend"), categories);
    }

    // Сортировка по часам, потом по id
    @Test
    void sortedByHoursThenId() {
        List<ProjectTask> sorted = new TaskAnalytics().sortedByHoursThenId(sampleTasks());

        assertEquals(4, sorted.size());
        assertEquals("T-4", sorted.get(0).getId());    // 2 часа
        assertEquals("T-1", sorted.get(1).getId());    // 4 часа
        assertEquals("T-2", sorted.get(2).getId());    // 8 часов, id T-2 < T-3
        assertEquals("T-3", sorted.get(3).getId());    // 8 часов
    }

    // Исходный список НЕ изменился
    @Test
    void originalListIsNotModified() {
        List<ProjectTask> original = sampleTasks();
        List<String> originalIds = new ArrayList<>();
        for (ProjectTask t : original) originalIds.add(t.getId());

        new TaskAnalytics().sortedByHoursThenId(original);

        List<String> afterIds = new ArrayList<>();
        for (ProjectTask t : original) afterIds.add(t.getId());
        assertEquals(originalIds, afterIds);          // порядок не изменился
    }
}