package edu.course.lab03;

/**
 * Клиент, выбирающий более приоритетную задачу из двух.
 * Зависит от интерфейса PriorityPolicy.
 */
public final class TaskPrioritizer {

    private final PriorityPolicy policy;      // поле интерфейсного типа

    // Политика передаётся через конструктор, не создаётся внутри
    public TaskPrioritizer(PriorityPolicy policy) {
        if (policy == null) {
            throw new IllegalArgumentException("policy must not be null");
        }
        this.policy = policy;
    }

    // Возвращает более приоритетную из двух задач
    public ProjectTask pickHigher(ProjectTask a, ProjectTask b) {
        if (a == null || b == null) {                              // обе задачи не null
            throw new IllegalArgumentException("tasks must not be null");
        }
        int priorityA = policy.calculatePriority(a);                // приоритет первой
        int priorityB = policy.calculatePriority(b);                // приоритет второй
        return priorityA >= priorityB ? a : b;                     // тернарный оператор: если A ≥ B → A, иначе B
    }
}