package edu.course.lab04;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    // Два экземпляра с одинаковым ISBN равны
    @Test
    void booksWithSameIsbnAreEqual() {
        Book a = new Book("978-1", "Война и мир");
        Book b = new Book("978-1", "Другое название");
        assertTrue(a.equals(b));                        // equals по ISBN
        assertEquals(a.hashCode(), b.hashCode());       // hashCode тоже совпадает
    }

    // В HashMap значение по одному экземпляру находится по другому (тот же ISBN)
    @Test
    void hashMapFindsValueByEqualKey() {
        Map<Book, String> map = new HashMap<>();
        map.put(new Book("978-1", "Книга"), "полка 1");   // кладём с одним экземпляром
        assertEquals("полка 1", map.get(new Book("978-1", "Другая обложка")));
    }

    // Книги с разными ISBN не равны
    @Test
    void booksWithDifferentIsbnAreNotEqual() {
        Book a = new Book("978-1", "Книга");
        Book b = new Book("978-2", "Книга");
        assertNotEquals(a, b);
    }

    // equals с null и другим типом → false
    @Test
    void equalsReturnsFalseForNullOrOtherType() {
        Book a = new Book("978-1", "Книга");
        assertFalse(a.equals(null));                    // null → false
        assertFalse(a.equals("строка"));                // другой тип → false
    }
}