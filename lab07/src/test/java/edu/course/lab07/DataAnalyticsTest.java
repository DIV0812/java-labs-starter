package edu.course.lab07;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DataAnalyticsTest {

    private final DataAnalytics analytics = new DataAnalytics();

    private List<ProjectTask> sampleTasks() {
        List<ProjectTask> tasks = new ArrayList<>();
        tasks.add(new ProjectTask("T-3", "C", "backend",  8, TaskStatus.READY));
        tasks.add(new ProjectTask("T-1", "A", "frontend", 4, TaskStatus.IN_PROGRESS));
        tasks.add(new ProjectTask("T-2", "B", "backend",  8, TaskStatus.READY));
        tasks.add(new ProjectTask("T-4", "D", "backend",  2, TaskStatus.DONE));
        return tasks;
    }

    // ===== 1. filterByStatus =====

    @Test
    void filterByStatusReturnsMatching() {
        List<ProjectTask> result = analytics.filterByStatus(sampleTasks(), TaskStatus.READY);
        assertEquals(2, result.size());                              // T-3, T-2
        assertTrue(result.stream().allMatch(t -> t.getStatus() == TaskStatus.READY));
    }

    // Обязательный: пустой список
    @Test
    void filterByStatusEmptyList() {
        List<ProjectTask> result = analytics.filterByStatus(List.of(), TaskStatus.READY);
        assertTrue(result.isEmpty());
    }

    // Обязательный: один элемент
    @Test
    void filterByStatusOneElement() {
        List<ProjectTask> one = List.of(
                new ProjectTask("T-1", "A", "backend", 4, TaskStatus.DONE));
        List<ProjectTask> result = analytics.filterByStatus(one, TaskStatus.DONE);
        assertEquals(1, result.size());
    }

    // ===== 2. sortedNormalizedCategories =====

    @Test
    void sortedNormalizedCategories() {
        List<String> result = analytics.sortedNormalizedCategories(sampleTasks());
        assertEquals(List.of("BACKEND", "FRONTEND"), result);
    }

    // ===== 3. countByCategory =====

    @Test
    void countByCategoryGroupsCorrectly() {
        Map<String, Long> result = analytics.countByCategory(sampleTasks());
        assertEquals(3L, result.get("backend"));                     // T-3, T-2, T-4
        assertEquals(1L, result.get("frontend"));                    // T-1
    }

    // ===== 4. maxByHours — Optional =====

    @Test
    void maxByHoursReturnsMax() {
        Optional<ProjectTask> result = analytics.maxByHours(sampleTasks());
        assertTrue(result.isPresent());
        assertEquals(8, result.get().getEstimatedHours());           // max = 8
    }

    // Обязательный: отсутствие максимального элемента (пустой список)
    @Test
    void maxByHoursEmpty() {
        Optional<ProjectTask> result = analytics.maxByHours(List.of());
        assertTrue(result.isEmpty());                                // Optional.empty()
    }

    // ===== 5. sortedByHoursThenId =====

    @Test
    void sortedByHoursThenIdOrdersCorrectly() {
        List<ProjectTask> result = analytics.sortedByHoursThenId(sampleTasks());
        assertEquals(4, result.size());
        assertEquals("T-4", result.get(0).getId());    // 2 часа
        assertEquals("T-1", result.get(1).getId());    // 4 часа
        assertEquals("T-2", result.get(2).getId());    // 8 часов, T-2 < T-3
        assertEquals("T-3", result.get(3).getId());    // 8 часов
    }

    // Обязательный: равные основные ключи — проверка tie-break по id
    @Test
    void sortedByHoursThenIdTieBreak() {
        List<ProjectTask> tie = List.of(
                new ProjectTask("T-9", "X", "backend", 5, TaskStatus.READY),
                new ProjectTask("T-1", "Y", "backend", 5, TaskStatus.READY)
        );
        List<ProjectTask> result = analytics.sortedByHoursThenId(tie);
        assertEquals("T-1", result.get(0).getId());   // при равных часах — по id
        assertEquals("T-9", result.get(1).getId());
    }

    // Обязательный: исходный список не изменяется
    @Test
    void originalListIsNotModified() {
        List<ProjectTask> original = sampleTasks();
        List<String> before = new ArrayList<>();
        for (ProjectTask t : original) before.add(t.getId());

        analytics.sortedByHoursThenId(original);                     // вызываем
        analytics.filterByStatus(original, TaskStatus.READY);        // вызываем ещё

        List<String> after = new ArrayList<>();
        for (ProjectTask t : original) after.add(t.getId());
        assertEquals(before, after);                                 // порядок не изменился
    }
}