package edu.course.lab03;

/**
 * Приоритет по статусу: NEW — самый высокий, DONE — самый низкий.
 */
public final class StatusPriorityPolicy implements PriorityPolicy {

    @Override
    public int calculatePriority(ProjectTask task) {
        if (task == null) {                                       // защита от null
            throw new IllegalArgumentException("task must not be null");
        }
        switch (task.getStatus()) {                               // switch по enum
            case NEW:         return 4;                           // самая высокая
            case IN_PROGRESS: return 3;
            case CANCELLED:   return 2;
            case DONE:        return 1;                           // самая низкая
            default:          throw new IllegalStateException("unknown status");
        }
    }
}