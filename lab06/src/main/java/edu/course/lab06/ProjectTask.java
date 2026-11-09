package edu.course.lab06;

/**
 * Модель задачи: id, title, category, estimatedHours, status.
 * Все поля final — объект неизменяем.
 */
public final class ProjectTask {           // final — нельзя наследоваться

    private final String id;               // идентификатор
    private final String title;            // название
    private final String category;         // категория
    private final int estimatedHours;      // трудоёмкость в часах
    private final TaskStatus status;       // статус

    public ProjectTask(String id, String title, String category,
                       int estimatedHours, TaskStatus status) {
        if (id == null || id.isBlank()) {                                // id не пустой
            throw new IllegalArgumentException("id must not be blank");
        }
        if (title == null || title.isBlank()) {                          // title не пустой
            throw new IllegalArgumentException("title must not be blank");
        }
        if (category == null || category.isBlank()) {                    // category не пустая
            throw new IllegalArgumentException("category must not be blank");
        }
        if (estimatedHours <= 0) {                                       // часы > 0
            throw new IllegalArgumentException("estimatedHours must be positive");
        }
        if (status == null) {                                            // статус не null
            throw new IllegalArgumentException("status must not be null");
        }
        this.id = id;
        this.title = title;
        this.category = category;
        this.estimatedHours = estimatedHours;
        this.status = status;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public int getEstimatedHours() { return estimatedHours; }
    public TaskStatus getStatus() { return status; }

    @Override
    public String toString() {
        return "ProjectTask{" + id + ", " + title + ", " + category
                + ", " + estimatedHours + ", " + status + "}";
    }
}