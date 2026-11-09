package edu.course.lab07;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Аналитика по задачам через Stream API.
 * Все методы: входной список не меняется, обычных циклов нет.
 */
public final class DataAnalytics {

    /**
     * 1. Отфильтровать задачи по статусу.
     * filter — промежуточная операция, toList — терминальная.
     */
    public List<ProjectTask> filterByStatus(List<ProjectTask> tasks, TaskStatus status) {
        if (tasks == null) throw new IllegalArgumentException("tasks must not be null");
        if (status == null) throw new IllegalArgumentException("status must not be null");

        return tasks.stream()                                       // 1. поток из списка
                .filter(t -> t.getStatus() == status)               // 2. промежуточная: фильтр
                .toList();                                          // 3. терминальная: список
    }

    /**
     * 2. Отсортированный список нормализованных категорий.
     * Нормализация: trim + toUpperCase.
     */
    public List<String> sortedNormalizedCategories(List<ProjectTask> tasks) {
        if (tasks == null) throw new IllegalArgumentException("tasks must not be null");

        return tasks.stream()
                .map(ProjectTask::getCategory)                      // 1. map: задача → категория
                .map(String::trim)                                  // 2. trim
                .map(String::toUpperCase)                           // 3. в верхний регистр
                .distinct()                                         // 4. без дубликатов
                .sorted()                                           // 5. по алфавиту
                .toList();
    }

    /**
     * 3. Число объектов по категории: groupingBy + counting.
     */
    public Map<String, Long> countByCategory(List<ProjectTask> tasks) {
        if (tasks == null) throw new IllegalArgumentException("tasks must not be null");

        return tasks.stream()
                .collect(Collectors.groupingBy(                     // группировка
                        ProjectTask::getCategory,                   // ключ — категория
                        Collectors.counting()                       // значение — количество
                ));
    }

    /**
     * 4. Задача с максимальной трудоёмкостью. Optional — если список пуст.
     */
    public Optional<ProjectTask> maxByHours(List<ProjectTask> tasks) {
        if (tasks == null) throw new IllegalArgumentException("tasks must not be null");

        return tasks.stream()
                .max(Comparator.comparingInt(ProjectTask::getEstimatedHours));
        // max — терминальная; возвращает Optional.empty() для пустого потока
    }

    /**
     * 5. Новый список, отсортированный по трудоёмкости, затем по id.
     * Исходный список не изменяется (stream не мутирует список).
     */
    public List<ProjectTask> sortedByHoursThenId(List<ProjectTask> tasks) {
        if (tasks == null) throw new IllegalArgumentException("tasks must not be null");

        return tasks.stream()
                .sorted(Comparator
                        .comparingInt(ProjectTask::getEstimatedHours)   // 1-й ключ: часы
                        .thenComparing(ProjectTask::getId))             // 2-й ключ: id
                .toList();
    }
}