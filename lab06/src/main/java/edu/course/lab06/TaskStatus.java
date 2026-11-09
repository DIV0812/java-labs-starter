package edu.course.lab06;      // пакет модуля lab06

/**
 * Статус задачи.
 */
public enum TaskStatus {      // enum — фиксированный набор
    READY,                    // готова к работе
    IN_PROGRESS,              // в работе
    DONE,                     // завершена
    CANCELLED                 // отменена
}