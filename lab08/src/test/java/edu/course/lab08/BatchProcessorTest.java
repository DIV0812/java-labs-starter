package edu.course.lab08;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.junit.jupiter.api.Assertions.*;

class BatchProcessorTest {

    private final BatchProcessor<Integer, Integer> processor = new BatchProcessor<>();
    private final TaskFunction<Integer, Integer> doubleIt = x -> x * 2;

    // ===== ПОСЛЕДОВАТЕЛЬНЫЙ МЕТОД =====

    @Test
    void sequentialEmptyList() {
        List<TaskResult<Integer>> result = processor.processSequentially(List.of(), doubleIt);
        assertTrue(result.isEmpty());
    }

    @Test
    void sequentialSeveralSuccessful() {
        List<TaskResult<Integer>> result = processor.processSequentially(List.of(1, 2, 3), doubleIt);
        assertEquals(3, result.size());
        assertEquals(2, result.get(0).value());
        assertEquals(4, result.get(1).value());
        assertEquals(6, result.get(2).value());
        assertTrue(result.stream().allMatch(TaskResult::isSuccess));
    }

    @Test
    void sequentialOneFailingKeepsOthers() {
        TaskFunction<Integer, Integer> failOnTwo = x -> {
            if (x == 2) throw new RuntimeException("boom");
            return x * 2;
        };

        List<TaskResult<Integer>> result = processor.processSequentially(List.of(1, 2, 3), failOnTwo);

        assertEquals(3, result.size());
        assertTrue(result.get(0).isSuccess());
        assertFalse(result.get(1).isSuccess());
        assertNotNull(result.get(1).error());
        assertTrue(result.get(2).isSuccess());
    }

    @Test
    void sequentialDoesNotModifyInput() {
        List<Integer> input = new ArrayList<>(List.of(1, 2, 3));
        processor.processSequentially(input, doubleIt);
        assertEquals(List.of(1, 2, 3), input);
    }

    // ===== КОНКУРЕНТНЫЙ МЕТОД =====

    @Test
    void concurrentEmptyList() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            List<TaskResult<Integer>> result = processor.processConcurrently(List.of(), doubleIt, executor);
            assertTrue(result.isEmpty());
        } finally {
            executor.shutdown();
        }
    }

    @Test
    void concurrentSeveralSuccessful() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            List<TaskResult<Integer>> result = processor.processConcurrently(List.of(1, 2, 3), doubleIt, executor);
            assertEquals(3, result.size());
            assertEquals(2, result.get(0).value());
            assertEquals(4, result.get(1).value());
            assertEquals(6, result.get(2).value());
        } finally {
            executor.shutdown();
        }
    }

    @Test
    void concurrentPreservesOrderDespiteDifferentDurations() throws InterruptedException {
        TaskFunction<Integer, Integer> variable = x -> {
            if (x == 1) {
                long end = System.nanoTime() + 50_000_000L;
                while (System.nanoTime() < end) { /* spin */ }
            }
            return x * 2;
        };

        ExecutorService executor = Executors.newFixedThreadPool(3);
        try {
            List<TaskResult<Integer>> result = processor.processConcurrently(List.of(1, 2, 3), variable, executor);
            assertEquals(2, result.get(0).value());
            assertEquals(4, result.get(1).value());
            assertEquals(6, result.get(2).value());
        } finally {
            executor.shutdown();
        }
    }

    @Test
    void concurrentOneFailingKeepsOthers() throws InterruptedException {
        TaskFunction<Integer, Integer> failOnTwo = x -> {
            if (x == 2) throw new RuntimeException("boom");
            return x * 2;
        };

        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            List<TaskResult<Integer>> result = processor.processConcurrently(List.of(1, 2, 3), failOnTwo, executor);
            assertEquals(3, result.size());
            assertTrue(result.get(0).isSuccess());
            assertFalse(result.get(1).isSuccess());
            assertNotNull(result.get(1).error());
            assertTrue(result.get(2).isSuccess());
        } finally {
            executor.shutdown();
        }
    }

    @Test
    void concurrentDoesNotModifyInput() throws InterruptedException {
        List<Integer> input = new ArrayList<>(List.of(1, 2, 3));
        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            processor.processConcurrently(input, doubleIt, executor);
            assertEquals(List.of(1, 2, 3), input);
        } finally {
            executor.shutdown();
        }
    }

    @Test
    void executorIsShutdownByCaller() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            processor.processConcurrently(List.of(1, 2, 3), doubleIt, executor);
            assertFalse(executor.isShutdown());
        } finally {
            executor.shutdown();
        }
        assertTrue(executor.isShutdown());
    }
}