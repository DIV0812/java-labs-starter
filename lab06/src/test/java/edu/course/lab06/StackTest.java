package edu.course.lab06;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class StackTest {

    // Пустой стек
    @Test
    void emptyStack() {
        Stack<String> stack = new Stack<>();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    // push + peek + pop для String
    @Test
    void pushPeekPopString() {
        Stack<String> stack = new Stack<>();
        stack.push("a");
        stack.push("b");
        assertEquals(2, stack.size());
        assertEquals("b", stack.peek());        // верхушка — b
        assertEquals("b", stack.pop());         // pop удаляет
        assertEquals("a", stack.peek());        // теперь верхушка — a
        assertEquals(1, stack.size());
    }

    // Stack<ProjectTask> — второй тип
    @Test
    void stackOfProjectTasks() {
        Stack<ProjectTask> stack = new Stack<>();
        ProjectTask t1 = new ProjectTask("T-1", "A", "backend", 4, TaskStatus.READY);
        ProjectTask t2 = new ProjectTask("T-2", "B", "frontend", 8, TaskStatus.IN_PROGRESS);

        stack.push(t1);
        stack.push(t2);

        assertEquals(t2, stack.pop());          // порядок LIFO
        assertEquals(t1, stack.pop());
        assertTrue(stack.isEmpty());
    }

    // pop пустого стека → NoSuchElementException
    @Test
    void popEmptyThrows() {
        Stack<String> stack = new Stack<>();
        assertThrows(NoSuchElementException.class, stack::pop);
    }

    // peek пустого стека → NoSuchElementException
    @Test
    void peekEmptyThrows() {
        Stack<String> stack = new Stack<>();
        assertThrows(NoSuchElementException.class, stack::peek);
    }

    // push(null) → IllegalArgumentException
    @Test
    void pushNullThrows() {
        Stack<String> stack = new Stack<>();
        assertThrows(IllegalArgumentException.class, () -> stack.push(null));
    }
}