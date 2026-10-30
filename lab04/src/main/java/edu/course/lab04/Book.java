package edu.course.lab04;

import java.util.Objects;      // Objects.equals и Objects.hash

/**
 * Книга: неизменяемый класс, равенство по ISBN.
 */
public final class Book {                   // final — нельзя наследоваться

    private final String isbn;              // final — неизменяемо
    private final String title;             // final — неизменяемо

    // Конструктор с проверками
    public Book(String isbn, String title) {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("isbn must not be null or blank");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title must not be null or blank");
        }
        this.isbn = isbn;
        this.title = title;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }

    // Две книги равны, если совпадает ISBN
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;                              // рефлексивность: тот же объект
        if (o == null || getClass() != o.getClass()) return false;  // null или другой тип → false
        Book book = (Book) o;                                    // приведение после проверки типа
        return Objects.equals(isbn, book.isbn);                  // сравнение по ISBN
    }

    // hashCode зависит от тех же полей, что equals
    @Override
    public int hashCode() {
        return Objects.hash(isbn);                               // только isbn — как в equals
    }

    // toString для читаемого вывода
    @Override
    public String toString() {
        return "Book{isbn='" + isbn + "', title='" + title + "'}";
    }
}
