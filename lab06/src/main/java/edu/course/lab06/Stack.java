package edu.course.lab06;

import java.util.ArrayList;             // внутреннее хранилище
import java.util.List;
import java.util.NoSuchElementException;  // для пустого стека

/**
 * Учебный стек на основе ArrayList<T>.
 * null запрещён; pop/peek пустого стека → NoSuchElementException.
 */
public final class Stack<T> {           // generic-класс

    private final List<T> items = new ArrayList<>();   // внутренний список

    public void push(T value) {                         // положить наверх
        if (value == null) {                            // null запрещён
            throw new IllegalArgumentException("value must not be null");
        }
        items.add(value);                               // добавляем в конец — верхушка
    }

    public T pop() {                                    // снять с верхушки
        if (items.isEmpty()) {                          // пустой стек → исключение
            throw new NoSuchElementException("stack is empty");
        }
        return items.remove(items.size() - 1);          // удаляем последний
    }

    public T peek() {                                   // посмотреть верхушку
        if (items.isEmpty()) {                          // пустой стек → исключение
            throw new NoSuchElementException("stack is empty");
        }
        return items.get(items.size() - 1);             // читаем последний
    }

    public int size() {                                 // количество
        return items.size();
    }

    public boolean isEmpty() {                          // пустой ли
        return items.isEmpty();
    }
}