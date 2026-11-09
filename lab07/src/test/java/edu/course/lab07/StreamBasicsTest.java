package edu.course.lab07;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StreamBasicsTest {

    @Test
    void streamPipelineProducesExpectedResult() {
        // Исходный список — изменяемый
        List<String> source = new ArrayList<>(List.of("java", "", "git", "maven", "api"));

        // Копия ДО обработки
        List<String> before = new ArrayList<>(source);

        // Stream pipeline
        List<String> result = source.stream()
                .filter(s -> !s.isEmpty())              // 1. непустые
                .map(String::toUpperCase)               // 2. в верхний регистр
                .sorted(Comparator
                        .comparingInt(String::length)   // 3. по длине
                        .thenComparing(Comparator.naturalOrder()))  // 4. затем по алфавиту
                .toList();

        // Проверка результата
        assertEquals(List.of("API", "GIT", "JAVA", "MAVEN"), result);

        // Проверка: исходный список НЕ изменился
        assertEquals(before, source);
    }
}
