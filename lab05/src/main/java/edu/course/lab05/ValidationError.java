package edu.course.lab05;

/**
 * Ошибка одной строки: номер строки, поле, сообщение.
 */
public final class ValidationError {

    private final int lineNumber;      // номер строки в файле (с 1)
    private final String field;        // какое поле вызвало ошибку
    private final String message;      // текст ошибки

    public ValidationError(int lineNumber, String field, String message) {
        if (lineNumber <= 0) {
            throw new IllegalArgumentException("lineNumber must be positive");
        }
        if (field == null || field.isBlank()) {
            throw new IllegalArgumentException("field must not be blank");
        }
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("message must not be blank");
        }
        this.lineNumber = lineNumber;
        this.field = field;
        this.message = message;
    }

    public int getLineNumber() { return lineNumber; }
    public String getField() { return field; }
    public String getMessage() { return message; }

    @Override
    public String toString() {
        return "Line " + lineNumber + ", field '" + field + "': " + message;
    }
}