package edu.course.lab04;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IssueRegistryTest {

    // Эксперимент 1: два разных экземпляра равного ключа находят одно значение
    @Test
    void equalKeysFindSameValue() {
        IssueRegistry registry = new IssueRegistry();
        ProjectTask task = new ProjectTask("T-1", "Fix bug", 4);

        registry.put(new IssueId("PRJ", 10), task);                      // кладём
        assertSame(task, registry.get(new IssueId("PRJ", 10)));          // ищем другим экземпляром
    }

    // Эксперимент 2: повторное добавление заменяет значение и не увеличивает размер
    @Test
    void putWithEqualKeyReplacesValue() {
        IssueRegistry registry = new IssueRegistry();
        ProjectTask first = new ProjectTask("T-1", "First", 1);
        ProjectTask second = new ProjectTask("T-2", "Second", 2);

        registry.put(new IssueId("PRJ", 10), first);
        registry.put(new IssueId("PRJ", 10), second);                    // тот же ключ

        assertEquals(1, registry.size());                                // размер не вырос
        assertSame(second, registry.get(new IssueId("PRJ", 10)));        // значение заменено
    }

    // Эксперимент 3: коллизия — разные ключи с одинаковым hashCode
    @Test
    void collidingKeysKeepDifferentValues() {
        IssueRegistry registry = new IssueRegistry();

        // Специально создаём класс-ключ с фиксированным hashCode
        class CollidingKey {
            final String name;
            CollidingKey(String name) { this.name = name; }
            @Override public int hashCode() { return 42; }               // ОДИН hashCode для всех
            @Override public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof CollidingKey)) return false;
                CollidingKey other = (CollidingKey) o;
                return name.equals(other.name);                          // разные name → разные ключи
            }
        }

        // Используем два одинаковых hashCode, но разных по equals ключа.
        // Но IssueRegistry принимает только IssueId — проверим коллизию на HashMap напрямую.
        java.util.Map<CollidingKey, String> map = new java.util.HashMap<>();
        map.put(new CollidingKey("a"), "значение A");
        map.put(new CollidingKey("b"), "значение B");                    // коллизия hashCode

        assertEquals("значение A", map.get(new CollidingKey("a")));
        assertEquals("значение B", map.get(new CollidingKey("b")));      // не смешалось
    }

    // Эксперимент 4: сравнение с null и другим типом для ключа
    @Test
    void equalsWithNullAndOtherType() {
        IssueId key = new IssueId("PRJ", 10);
        assertFalse(key.equals(null));                                    // null → false
        assertFalse(key.equals("PRJ-10"));                                // другой тип → false
    }

    // Эксперимент 5: изменяемый ключ ломает поиск
    @Test
    void mutableKeyBreaksLookup() {
        // Учебный «изменяемый» ключ — только для эксперимента
        class MutableKey {
            String name;                                                 // НЕ final
            MutableKey(String name) { this.name = name; }
            @Override public int hashCode() { return name.hashCode(); }
            @Override public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof MutableKey)) return false;
                return name.equals(((MutableKey) o).name);
            }
        }

        java.util.Map<MutableKey, String> map = new java.util.HashMap<>();
        MutableKey key = new MutableKey("old");
        map.put(key, "значение");                                        // кладём

        assertNotNull(map.get(new MutableKey("old")));                  // пока не меняли — находится

        key.name = "changed";                                            // ⚠️ меняем поле, участвующее в hashCode

        // Теперь поиск по «старой» логике не находит — hashCode изменился
        assertNull(map.get(new MutableKey("old")));
    }

    // Обычные операции: put / get / remove / contains / size
    @Test
    void basicOperations() {
        IssueRegistry registry = new IssueRegistry();
        IssueId id = new IssueId("PRJ", 1);
        ProjectTask task = new ProjectTask("T-1", "Title", 2);

        assertTrue(registry.size() == 0);
        registry.put(id, task);
        assertEquals(1, registry.size());
        assertTrue(registry.contains(id));
        assertSame(task, registry.get(id));

        registry.remove(id);
        assertFalse(registry.contains(id));
        assertEquals(0, registry.size());
    }
}