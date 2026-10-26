package edu.course.lab04;

/**
 * Минимальная версия задачи для Lab04.
 * Идентификатор и название неизменяемы.
 */
public final class ProjectTask {          // final — нельзя наследоваться

    private final String id;              // id неизменяем
    private final String title;           // title неизменяем
    private TaskStatus status;            // статус меняется
    private int estimatedHours;           // трудоёмкость

    // Конструктор с проверками
    public ProjectTask(String id, String title, int estimatedHours) {
        if (id == null || id.isBlank()) {                             // id не null и не пустой
            throw new IllegalArgumentException("id must not be null or blank");
        }
        if (title == null || title.isBlank()) {                       // title не null и не пустой
            throw new IllegalArgumentException("title must not be null or blank");
        }
        if (estimatedHours <= 0) {                                    // часы положительные
            throw new IllegalArgumentException("estimatedHours must be positive");
        }
        this.id = id;
        this.title = title;
        this.status = TaskStatus.NEW;                                 // начальный статус
        this.estimatedHours = estimatedHours;
    }

    // Геттеры — только чтение
    public String getId() { return id; }
    public String getTitle() { return title; }
    public TaskStatus getStatus() { return status; }
    public int getEstimatedHours() { return estimatedHours; }

    // Смена статуса — единственный способ
    public void changeStatus(TaskStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("newStatus must not be null");
        }
        this.status = newStatus;
    }

    public boolean isCompleted() {
        return status == TaskStatus.DONE;                             // true только для DONE
    }

    public void addHours(int extraHours) {
        if (extraHours <= 0) {
            throw new IllegalArgumentException("extraHours must be positive");
        }
        this.estimatedHours += extraHours;
    }
}