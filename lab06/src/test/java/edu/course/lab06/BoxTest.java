package edu.course.lab06;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoxTest {

    // Box<String>: принимает и возвращает String
    @Test
    void boxOfString() {
        Box<String> box = new Box<>("hello");       // T = String
        assertEquals("hello", box.get());           // get() возвращает String без приведения
        box.set("world");                           // set принимает String
        assertEquals("world", box.get());
    }

    // Box<Integer>: принимает и возвращает Integer
    @Test
    void boxOfInteger() {
        Box<Integer> box = new Box<>(42);           // T = Integer
        assertEquals(42, box.get());
        box.set(100);
        assertEquals(100, box.get());
    }

    /*
     * ПРИМЕР НЕКОМПИЛИРУЕМОЙ СТРОКИ (специально закомментировано):
     *
     * Box<String> box = new Box<>("hello");
     * box.set(42);   //  ОШИБКА КОМПИЛЯЦИИ: int нельзя передать в Box<String>
     *
     * Параметр типа T гарантирует, что в Box<String> можно положить только String.
     * Без generics пришлось бы делать приведение и ловить ClassCastException в рантайме.
     */
}