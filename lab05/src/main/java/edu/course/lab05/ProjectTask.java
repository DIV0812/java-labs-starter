package edu.course.lab05;

/**
 * Модель задачи из файла.
 * Поля: id, title, category, estimatedHours, status.
 */
public final class ProjectTask {           // final — нельзя наследоваться

    private final String id;               // идентификатор
    private final String title;            // название
    private final String category;         // категория
    private final int estimatedHours;      // трудоёмкость в часах
    private final TaskStatus status;       // статус

    // Конструктор с проверками (все поля обязательны)
    public ProjectTask(String id, String title, String category,
                       int estimatedHours, TaskStatus status) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id must not be blank");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title must not be blank");
        }
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("category must not be blank");
        }
        if (estimatedHours <= 0) {
            throw new IllegalArgumentException("estimatedHours must be positive");
        }
        if (status == null) {
            throw new IllegalArgumentException("status must not be null");
        }
        this.id = id;
        this.title = title;
        this.category = category;
        this.estimatedHours = estimatedHours;
        this.status = status;
    }

    // Геттеры — только чтение
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