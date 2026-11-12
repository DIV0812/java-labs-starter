package edu.course.lab08;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ExecutorBasicsTest {

    @Test
    void threeCallablesReturnValuesInOrder() throws Exception {
        // Создаем executor с фиксированным пулом на 2 потока
        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            // Создаем 3 задачи (Callable), которые возвращают 10, 20, 30
            List<Callable<Integer>> tasks = List.of(
                    () -> 10,
                    () -> 20,
                    () -> 30
            );

            // Отправляем задачи в executor в порядке 10 -> 20 -> 30.
            // executor.submit() возвращает Future — "обещание" результата.
            List<Future<Integer>> futures = new ArrayList<>();
            for (Callable<Integer> task : tasks) {
                futures.add(executor.submit(task));
            }

            // Собираем результаты.
            // Мы идем по futures в том же порядке, в котором их отправляли.
            // future.get() блокирует поток, пока задача не выполнится.
            List<Integer> results = new ArrayList<>();
            for (Future<Integer> future : futures) {
                results.add(future.get());
            }

            // Проверяем, что порядок сохранился
            assertEquals(List.of(10, 20, 30), results);
        } finally {
            // ОБЯЗАТЕЛЬНО: завершаем executor, иначе потоки останутся висеть и JVM не завершится.
            executor.shutdown();
        }
    }
}