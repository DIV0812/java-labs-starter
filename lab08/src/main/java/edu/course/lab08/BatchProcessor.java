package edu.course.lab08;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Последовательная и конкурентная обработка списка входных данных.
 * Позволяет обрабатывать элементы списка, собирая как успешные результаты, так и ошибки.
 */
public final class BatchProcessor<T, R> {

    /**
     * Обрабатывает входы последовательно (в одном потоке).
     * Ошибка одного элемента не останавливает обработку остальных.
     */
    public List<TaskResult<R>> processSequentially(
            List<T> inputs,
            TaskFunction<T, R> function) {

        if (inputs == null) throw new IllegalArgumentException("inputs must not be null");
        if (function == null) throw new IllegalArgumentException("function must not be null");

        // Заранее задаем размер списка, чтобы избежать перевыделения памяти при расширении
        List<TaskResult<R>> results = new ArrayList<>(inputs.size());

        for (int i = 0; i < inputs.size(); i++) {
            try {
                // Вызываем функцию. Если внутри произойдет ошибка, она вылетит в catch
                R value = function.apply(inputs.get(i));
                // Если ошибки не было — записываем успех с индексом i
                results.add(TaskResult.success(i, value));
            } catch (Exception e) {
                // Если функция выбросила исключение, мы ловим его и оборачиваем в TaskResult
                // ВАЖНО: мы не прерываем цикл, а идем к следующему элементу (i++)
                results.add(TaskResult.failure(i, e));
            }
        }
        return results;
    }

    /**
     * Обрабатывает входы конкурентно (в нескольких потоках через ExecutorService).
     * НЕ завершает executor: им владеет вызывающий код.
     */
    public List<TaskResult<R>> processConcurrently(
            List<T> inputs,
            TaskFunction<T, R> function,
            ExecutorService executor) throws InterruptedException {

        if (inputs == null) throw new IllegalArgumentException("inputs must not be null");
        if (function == null) throw new IllegalArgumentException("function must not be null");
        if (executor == null) throw new IllegalArgumentException("executor must not be null");

        // Список futures в порядке отправки — это КЛЮЧ к сохранению порядка результатов.
        // Мы отправляем задачи в executor в том же порядке, в котором они лежат во входном списке.
        List<Future<TaskResult<R>>> futures = new ArrayList<>(inputs.size());

        for (int i = 0; i < inputs.size(); i++) {
            final int index = i;  // обязательная переменная final для использования внутри лямбды
            final T input = inputs.get(i);

            // Callable — это задача, которая может вернуть результат и выбросить checked-исключение.
            // Мы оборачиваем вызов функции в Callable, чтобы отдать её в executor.submit().
            Callable<TaskResult<R>> task = () -> {
                try {
                    R value = function.apply(input);
                    // Если ошибки нет — возвращаем TaskResult.success
                    return TaskResult.success(index, value);
                } catch (Exception e) {
                    // ВАЖНО: Ошибку ловим ВНУТРИ Callable. Если этого не сделать,
                    // она вылетит наружу в виде ExecutionException, и мы потеряем индекс.
                    return TaskResult.failure(index, e);
                }
            };

            // submit() немедленно возвращает Future (обещание результата).
            // Задача начнет выполняться в одном из потоков executor'а.
            futures.add(executor.submit(task));
        }

        // Собираем результаты в порядке, в котором мы отправляли задачи
        List<TaskResult<R>> results = new ArrayList<>(inputs.size());
        for (Future<TaskResult<R>> future : futures) {
            try {
                // future.get() блокирует текущий поток, пока задача не завершится.
                // Так как мы идем по futures в порядке отправки, результаты тоже будут в порядке.
                results.add(future.get());
            } catch (ExecutionException e) {
                // Сюда мы попасть почти не должны, так как ловим все исключения внутри Callable.
                // Но если вдруг что-то пошло не так (например, ошибка в самом Callable), обрабатываем.
                results.add(TaskResult.failure(results.size(), e));
            }
            // InterruptedException мы НЕ ловим.
            // Он пробрасывается выше по контракту: если поток прервали, мы должны дать об этом знать.
        }
        return results;
    }
}