package edu.course.lab05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;                       // @TempDir

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TempFileBasicsTest {

    @TempDir
    Path tempDir;                    // JUnit создаёт временную папку

    @Test
    void readLinesFromTempFile() throws IOException {
        Path file = tempDir.resolve("numbers.txt");
        Files.write(file, List.of("10", "bad", "20"), StandardCharsets.UTF_8);   // явно UTF-8

        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        assertEquals(List.of("10", "bad", "20"), lines);                       // содержимое совпало
    }

    @Test
    void badLineThrowsNumberFormatException() throws IOException {
        Path file = tempDir.resolve("numbers.txt");
        Files.write(file, List.of("10", "bad", "20"), StandardCharsets.UTF_8);

        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);

        int sum = 0;
        for (String line : lines) {
            if (line.equals("bad")) {
                assertThrows(NumberFormatException.class,
                        () -> Integer.parseInt(line));                          // именно bad → исключение
            } else {
                sum += Integer.parseInt(line);
            }
        }
        assertEquals(30, sum);                                                  // 10 + 20
    }
}