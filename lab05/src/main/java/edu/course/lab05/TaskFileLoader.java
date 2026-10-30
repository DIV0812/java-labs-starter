package edu.course.lab05;

import java.io.IOException;                       // checked исключение ввода-вывода
import java.nio.charset.StandardCharsets;         // UTF-8
import java.nio.file.Files;                       // Files.readAllLines
import java.nio.file.Path;                        // путь к файлу
import java.util.ArrayList;
import java.util.List;

/**
 * Парсер файла задач формата:
 * id;title;category;estimatedHours;status
 */
public final class TaskFileLoader {

    private static final String HEADER = "id;title;category;estimatedHours;status";
    private static final int EXPECTED_FIELDS = 5;

    /**
     * Читает файл, возвращает LoadResult.
     * Если файл недоступен — DataLoadingException с cause.
     * Ошибки отдельных строк — в списке errors, парсинг продолжается.
     */
    public LoadResult load(Path path) {
        if (path == null) {
            throw new IllegalArgumentException("path must not be null");
        }

        List<String> lines;
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);   // явно UTF-8
        } catch (IOException e) {
            // Ошибка источника: сохраняем cause
            throw new DataLoadingException("Cannot read " + path, e);
        }

        List<ProjectTask> tasks = new ArrayList<>();
        List<ValidationError> errors = new ArrayList<>();

        // Заголовок проверяется отдельно
        if (lines.isEmpty() || !HEADER.equals(lines.get(0))) {
            errors.add(new ValidationError(1, "header", "invalid or missing header"));
            return new LoadResult(tasks, errors);
        }

        // Строки со 2-й (индекс 1)
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            int lineNumber = i + 1;                                 // для человека — с 1

            if (line.isBlank()) continue;                           // пустые строки пропускаем

            String[] parts = line.split(";", -1);                   // -1 — сохранить пустые в конце
            if (parts.length != EXPECTED_FIELDS) {                  // неверное число полей
                errors.add(new ValidationError(lineNumber, "row",
                        "expected " + EXPECTED_FIELDS + " fields, got " + parts.length));
                continue;                                           // идём к следующей строке
            }

            String id = parts[0];
            String title = parts[1];
            String category = parts[2];
            String hoursText = parts[3];
            String statusText = parts[4];

            // Пустые поля — ошибка
            if (id.isBlank()) {
                errors.add(new ValidationError(lineNumber, "id", "must not be blank"));
                continue;
            }
            if (title.isBlank()) {
                errors.add(new ValidationError(lineNumber, "title", "must not be blank"));
                continue;
            }
            if (category.isBlank()) {
                errors.add(new ValidationError(lineNumber, "category", "must not be blank"));
                continue;
            }

            // Число
            int hours;
            try {
                hours = Integer.parseInt(hoursText);
            } catch (NumberFormatException e) {
                errors.add(new ValidationError(lineNumber, "estimatedHours",
                        "invalid number: " + hoursText));
                continue;
            }
            if (hours <= 0) {
                errors.add(new ValidationError(lineNumber, "estimatedHours",
                        "must be positive"));
                continue;
            }

            // Статус
            TaskStatus status;
            try {
                status = TaskStatus.valueOf(statusText);
            } catch (IllegalArgumentException e) {
                errors.add(new ValidationError(lineNumber, "status",
                        "unknown status: " + statusText));
                continue;
            }

            // Все проверки прошли — добавляем
            tasks.add(new ProjectTask(id, title, category, hours, status));
        }

        return new LoadResult(tasks, errors);
    }
}