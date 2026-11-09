package edu.course.lab06;

import java.util.ArrayList;
import java.util.Comparator;                          // для сортировки
import java.util.HashMap;                             // реализация Map
import java.util.HashSet;                             // реализация Set
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Аналитика по задачам: группировка, уникальность, сортировка.
 * Все операции — обычными циклами, без Stream API.
 */
public final class TaskAnalytics {

    /** Число задач по статусам. */
    public Map<TaskStatus, Integer> countByStatus(List<ProjectTask> tasks) {
        if (tasks == null) {
            throw new IllegalArgumentException("tasks must not be null");
        }
        Map<TaskStatus, Integer> result = new HashMap<>();          // Map: статус → счётчик
        for (ProjectTask task : tasks) {                            // обычный for-each
            TaskStatus status = task.getStatus();
            result.put(status, result.getOrDefault(status, 0) + 1); // +1 к счётчику
        }
        return result;
    }

    /** Уникальные категории. */
    public Set<String> uniqueCategories(List<ProjectTask> tasks) {
        if (tasks == null) {
            throw new IllegalArgumentException("tasks must not be null");
        }
        Set<String> result = new HashSet<>();                       // Set: уникальность
        for (ProjectTask task : tasks) {
            result.add(task.getCategory());                         // дубликаты отсекаются
        }
        return result;
    }

    /**
     * Новый список, отсортированный по трудоёмкости, потом по id.
     * Исходный список не изменяется.
     */
    public List<ProjectTask> sortedByHoursThenId(List<ProjectTask> tasks) {
        if (tasks == null) {
            throw new IllegalArgumentException("tasks must not be null");
        }
        List<ProjectTask> copy = new ArrayList<>(tasks);            // копия — исходный не трогаем

        // Comparator: сначала по часам, потом по id
        Comparator<ProjectTask> comparator = new Comparator<ProjectTask>() {
            @Override
            public int compare(ProjectTask a, ProjectTask b) {
                int byHours = Integer.compare(a.getEstimatedHours(), b.getEstimatedHours());  // не вычитание
                if (byHours != 0) {
                    return byHours;
                }
                return a.getId().compareTo(b.getId());              // tie-break по id
            }
        };

        copy.sort(comparator);                                      // сортируем КОПИЮ
        return copy;
    }
}