package edu.course.lab05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Результат загрузки: успешные задачи + список ошибок.
 */
public final class LoadResult {

    private final List<ProjectTask> tasks;          // успешно распарсенные
    private final List<ValidationError> errors;     // ошибки записи

    public LoadResult(List<ProjectTask> tasks, List<ValidationError> errors) {
        // Оборачиваем в неизменяемые списки — снаружи нельзя изменить
        this.tasks = Collections.unmodifiableList(new ArrayList<>(tasks));
        this.errors = Collections.unmodifiableList(new ArrayList<>(errors));
    }

    public List<ProjectTask> getTasks() { return tasks; }
    public List<ValidationError> getErrors() { return errors; }

    public boolean hasErrors() { return !errors.isEmpty(); }
}