package edu.course.lab04;

import java.util.HashMap;      // импорт HashMap
import java.util.Map;          // интерфейс Map

/**
 * Реестр задач. Внутри — HashMap<IssueId, ProjectTask>.
 */
public final class IssueRegistry {

    private final Map<IssueId, ProjectTask> tasks = new HashMap<>();  // композиция: внутри HashMap

    /** Добавить или заменить задачу. Возвращает предыдущую (или null). */
    public ProjectTask put(IssueId id, ProjectTask task) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        if (task == null) {
            throw new IllegalArgumentException("task must not be null");
        }
        return tasks.put(id, task);                                   // put заменяет при равном ключе
    }

    /** Найти задачу по ключу. */
    public ProjectTask get(IssueId id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return tasks.get(id);                                         // поиск по equals+hashCode
    }

    /** Удалить задачу. Возвращает удалённую (или null). */
    public ProjectTask remove(IssueId id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return tasks.remove(id);
    }

    /** Есть ли ключ в реестре. */
    public boolean contains(IssueId id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return tasks.containsKey(id);
    }

    /** Количество задач. */
    public int size() {
        return tasks.size();
    }
}