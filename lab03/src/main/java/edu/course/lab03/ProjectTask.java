package edu.course.lab03;
/**
 * Задача проекта. Идентификатор и название неизменяемы.
 */
public final class ProjectTask {          // final — нельзя наследоваться

    private final String id;              // id неизменяем после создания
    private final String title;           // title тоже
    private TaskStatus status;            // НЕ final — статус меняется
    private int estimatedHours;           // трудоёмкость в часах

    // Конструктор с тремя проверками
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
        this.id = id;                                                 // this — обращение к полю класса
        this.title = title;
        this.status = TaskStatus.NEW;                                 // начальный статус — NEW
        this.estimatedHours = estimatedHours;
    }

    // Геттеры — только чтение
    public String getId() { return id; }
    public String getTitle() { return title; }
    public TaskStatus getStatus() { return status; }
    public int getEstimatedHours() { return estimatedHours; }

    // Смена статуса — единственный способ изменить
    public void changeStatus(TaskStatus newStatus) {
        if (newStatus == null) {                                      // null недопустим
            throw new IllegalArgumentException("newStatus must not be null");
        }
        this.status = newStatus;
    }

    // Поведенческий метод: true только для DONE
    public boolean isCompleted() {
        return status == TaskStatus.DONE;                             // сравнение ссылок на enum
    }

    // Увеличить трудоёмкость
    public void addHours(int extraHours) {
        if (extraHours <= 0) {                                        // только положительное
            throw new IllegalArgumentException("extraHours must be positive");
        }
        this.estimatedHours += extraHours;                            // += то же, что sum = sum + x
    }
}