package edu.course.lab05;      // пакет

/**
 * Статус задачи. READY — начальный статус задачи из файла.
 */
public enum TaskStatus {      // enum — фиксированный набор значений
    READY,                    // готова к работе
    IN_PROGRESS,              // в работе
    DONE,                     // завершена
    CANCELLED                 // отменена
}