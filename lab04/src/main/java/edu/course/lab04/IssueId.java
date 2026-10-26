package edu.course.lab04;

import java.util.Objects;

/**
 * Неизменяемый ключ: код проекта + номер задачи.
 */
public final class IssueId {                // final — нельзя наследоваться

    private final String projectCode;       // код проекта
    private final int number;               // номер задачи

    public IssueId(String projectCode, int number) {
        if (projectCode == null || projectCode.isBlank()) {
            throw new IllegalArgumentException("projectCode must not be null or blank");
        }
        if (number <= 0) {
            throw new IllegalArgumentException("number must be positive");
        }
        this.projectCode = projectCode;
        this.number = number;
    }

    public String getProjectCode() { return projectCode; }
    public int getNumber() { return number; }

    // Два IssueId равны, если совпадают ОБА поля
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssueId issueId = (IssueId) o;
        return number == issueId.number                            // примитив сравниваем через ==
                && Objects.equals(projectCode, issueId.projectCode); // String — через Objects.equals
    }

    // hashCode от тех же полей, что equals
    @Override
    public int hashCode() {
        return Objects.hash(projectCode, number);                 // оба поля
    }

    @Override
    public String toString() {
        return projectCode + "-" + number;
    }
}