package edu.course.lab05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskFileLoaderTest {

    @TempDir
    Path tempDir;

    private static final String HEADER = "id;title;category;estimatedHours;status";

    // Утилита для записи файла
    private Path writeFile(String name, String... lines) throws IOException {
        Path file = tempDir.resolve(name);
        Files.write(file, List.of(lines), StandardCharsets.UTF_8);
        return file;
    }

    // 1. Корректный файл
    @Test
    void loadsValidFile() throws IOException {
        Path file = writeFile("ok.txt", HEADER, "T-101;Prepare release;backend;8;READY");
        LoadResult result = new TaskFileLoader().load(file);

        assertEquals(0, result.getErrors().size());
        assertEquals(1, result.getTasks().size());
        ProjectTask t = result.getTasks().get(0);
        assertEquals("T-101", t.getId());
        assertEquals("Prepare release", t.getTitle());
        assertEquals("backend", t.getCategory());
        assertEquals(8, t.getEstimatedHours());
        assertEquals(TaskStatus.READY, t.getStatus());
    }

    // 2. Кириллица (UTF-8)
    @Test
    void loadsCyrillicTitle() throws IOException {
        Path file = writeFile("cyr.txt", HEADER, "T-1;Подготовить релиз;backend;4;READY");
        LoadResult result = new TaskFileLoader().load(file);

        assertEquals(0, result.getErrors().size());
        assertEquals("Подготовить релиз", result.getTasks().get(0).getTitle());
    }

    // 3. Пустое поле
    @Test
    void emptyFieldIsRecordedAsError() throws IOException {
        Path file = writeFile("empty.txt", HEADER, "T-1;;backend;4;READY");
        LoadResult result = new TaskFileLoader().load(file);

        assertEquals(0, result.getTasks().size());
        assertEquals(1, result.getErrors().size());
        assertEquals("title", result.getErrors().get(0).getField());
        assertEquals(2, result.getErrors().get(0).getLineNumber());
    }

    // 4. Неправильное число
    @Test
    void invalidNumberIsError() throws IOException {
        Path file = writeFile("badnum.txt", HEADER, "T-1;Title;backend;abc;READY");
        LoadResult result = new TaskFileLoader().load(file);

        assertEquals(0, result.getTasks().size());
        assertEquals(1, result.getErrors().size());
        assertEquals("estimatedHours", result.getErrors().get(0).getField());
    }

    // 5. Неизвестный статус
    @Test
    void unknownStatusIsError() throws IOException {
        Path file = writeFile("badstatus.txt", HEADER, "T-1;Title;backend;4;WHATEVER");
        LoadResult result = new TaskFileLoader().load(file);

        assertEquals(0, result.getTasks().size());
        assertEquals(1, result.getErrors().size());
        assertEquals("status", result.getErrors().get(0).getField());
    }

    // 6. Неверное число столбцов
    @Test
    void wrongColumnCountIsError() throws IOException {
        Path file = writeFile("badcols.txt", HEADER, "T-1;Title;backend;4");
        LoadResult result = new TaskFileLoader().load(file);

        assertEquals(0, result.getTasks().size());
        assertEquals(1, result.getErrors().size());
        assertEquals("row", result.getErrors().get(0).getField());
    }

    // 7. Корректные и ошибочные строки вместе
    @Test
    void mixedValidAndInvalidLines() throws IOException {
        Path file = writeFile("mixed.txt", HEADER,
                "T-1;Good;backend;4;READY",
                "T-2;;backend;4;READY",
                "T-3;Also good;backend;8;DONE");
        LoadResult result = new TaskFileLoader().load(file);

        assertEquals(2, result.getTasks().size());
        assertEquals(1, result.getErrors().size());
        assertEquals("title", result.getErrors().get(0).getField());
    }

    // 8. Отсутствующий файл
    @Test
    void missingFileThrowsDataLoadingException() {
        Path missing = tempDir.resolve("does-not-exist.txt");
        DataLoadingException ex = assertThrows(DataLoadingException.class,
                () -> new TaskFileLoader().load(missing));

        assertNotNull(ex.getCause());                                // cause сохранён
        assertTrue(ex.getCause() instanceof IOException);            // это IOException
    }
}