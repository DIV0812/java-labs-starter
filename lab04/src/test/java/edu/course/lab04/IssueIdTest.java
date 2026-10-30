package edu.course.lab04;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IssueIdTest {

    // Равные ключи → равные hashCode
    @Test
    void equalKeysHaveEqualHashCode() {
        IssueId a = new IssueId("PRJ", 10);
        IssueId b = new IssueId("PRJ", 10);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    // Разный номер → разные ключи
    @Test
    void differentNumberMeansNotEqual() {
        IssueId a = new IssueId("PRJ", 10);
        IssueId b = new IssueId("PRJ", 11);
        assertNotEquals(a, b);
    }

    // Разный код проекта → разные ключи
    @Test
    void differentProjectMeansNotEqual() {
        IssueId a = new IssueId("PRJ", 10);
        IssueId b = new IssueId("OTHER", 10);
        assertNotEquals(a, b);
    }

    // null и другой тип → false
    @Test
    void equalsWithNullAndDifferentType() {
        IssueId a = new IssueId("PRJ", 10);
        assertFalse(a.equals(null));
        assertFalse(a.equals("PRJ-10"));
    }

    // Конструктор: пустой код → исключение
    @Test
    void rejectsBlankProjectCode() {
        assertThrows(IllegalArgumentException.class, () -> new IssueId("", 1));
        assertThrows(IllegalArgumentException.class, () -> new IssueId(null, 1));
    }

    // Конструктор: номер ≤ 0 → исключение
    @Test
    void rejectsNonPositiveNumber() {
        assertThrows(IllegalArgumentException.class, () -> new IssueId("PRJ", 0));
        assertThrows(IllegalArgumentException.class, () -> new IssueId("PRJ", -1));
    }
}