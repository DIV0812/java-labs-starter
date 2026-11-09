package edu.course.lab07;

/**
 * Модель задачи: id, title, category, estimatedHours, status.
 * Все поля final — объект неизменяем.
 */
public final class ProjectTask {

    private final String id;
    private final String title;
    private final String category;
    private final int estimatedHours;
    private final TaskStatus status;

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