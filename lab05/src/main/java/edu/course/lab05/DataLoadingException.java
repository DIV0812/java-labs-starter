package edu.course.lab05;

/**
 * Ошибка источника: файл отсутствует или недоступен.
 * Runtime — checked не требуется.
 */
public final class DataLoadingException extends RuntimeException {

    // Конструктор принимает cause — сохраняем исходную причину
    public DataLoadingException(String message, Throwable cause) {
        super(message, cause);      // super() вызывает конструктор RuntimeException
    }
}