package edu.course.lab04;      // пакет, совпадает с папкой

/**
 * Статус задачи.
 */
public enum TaskStatus {      // enum — фиксированный набор значений
    NEW,                      // новая
    IN_PROGRESS,              // в работе
    DONE,                     // завершена
    CANCELLED                 // отменена
}