package edu.course.lab03;

/**
 * Приоритет = трудоёмкость. Чем больше часов, тем выше приоритет.
 */
public final class EffortPriorityPolicy implements PriorityPolicy {

    @Override
    public int calculatePriority(ProjectTask task) {
        if (task == null) {                                       // защита от null
            throw new IllegalArgumentException("task must not be null");
        }
        return task.getEstimatedHours();                          // возвращаем часы как приоритет
    }
}