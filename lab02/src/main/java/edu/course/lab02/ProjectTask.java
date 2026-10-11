package edu.course.lab02;          // пакет

/**
 * Задача проекта.
 * Инварианты: id и title не null и не пустые; estimatedHours > 0.
 */
public final class ProjectTask {  // final — от класса нельзя наследоваться

    private final String id;      // final — присваивается один раз, потом не меняется
    private final String title;   // final — то же самое
    private TaskStatus status;    // НЕ final — статус меняется через changeStatus
    private int estimatedHours;   // плановая трудоёмкость в часах

    // Конструктор: три проверки + инициализация полей
    public ProjectTask(String id, String title, int estimatedHours) {
        if (id == null || id.isBlank()) {                           // id не null и не пустой
            throw new IllegalArgumentException("id must not be null or blank");
        }
        if (title == null || title.isBlank()) {                     // title не null и не пустой
            throw new IllegalArgumentException("title must not be null or blank");
        }
        if (estimatedHours <= 0) {                                  // трудоёмкость положительная
            throw new IllegalArgumentException("estimatedHours must be positive");
        }
        this.id = id;                                               // сохраняем id
        this.title = title;                                         // сохраняем title
        this.status = TaskStatus.NEW;                               // начальный статус — NEW
        this.estimatedHours = estimatedHours;                       // сохраняем часы
    }

    // Геттеры: только чтение, без сеттеров
    public String getId() { return id; }
    public String getTitle() { return title; }
    public TaskStatus getStatus() { return status; }
    public int getEstimatedHours() { return estimatedHours; }

    // Единственный способ изменить статус
    public void changeStatus(TaskStatus newStatus) {
        if (newStatus == null) {                                    // null недопустим
            throw new IllegalArgumentException("newStatus must not be null");
        }
        this.status = newStatus;                                    // присваиваем новый статус
    }

    // Поведенческий метод: отвечает на вопрос «задача завершена?»
    public boolean isCompleted() {
        return status == TaskStatus.DONE;                           // true только для DONE
    }

    // Увеличить трудоёмкость. Заменить значение нельзя — только прибавить.
    public void addHours(int extraHours) {
        if (extraHours <= 0) {                                      // только положительное число
            throw new IllegalArgumentException("extraHours must be positive");
        }
        this.estimatedHours += extraHours;                          // прибавляем часы
    }
}
