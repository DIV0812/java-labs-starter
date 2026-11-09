package edu.course.lab06;

/**
 * Простейший generic-класс: хранит одно значение типа T.
 * T — параметр типа; подставляется при создании объекта.
 */
public final class Box<T> {               // <T> — параметр типа

    private T value;                      // поле типа T

    public Box(T value) {                 // конструктор принимает T
        this.value = value;
    }

    public T get() {                      // возвращает T — без приведения
        return value;
    }

    public void set(T value) {            // принимает T
        this.value = value;
    }
}
