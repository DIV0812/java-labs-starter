package edu.course.lab03;                    // тот же пакет

import org.junit.jupiter.api.Test;           // аннотация @Test

import static org.junit.jupiter.api.Assertions.assertEquals;      // статические импорты
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiscountPolicyTest {

    // NoDiscount: цена не меняется
    @Test
    void noDiscountReturnsSamePrice() {
        DiscountPolicy policy = new NoDiscount();                   // ссылка интерфейсного типа
        assertEquals(100.0, policy.apply(100.0), 0.0001);           // 100 → 100
    }

    // PercentDiscount(20): 100 → 80
    @Test
    void percentDiscountAppliesCorrectly() {
        DiscountPolicy policy = new PercentDiscount(20);            // 20%
        assertEquals(80.0, policy.apply(100.0), 0.0001);            // 100 * 0.8 = 80
    }

    // PercentDiscount(100): 100 → 0
    @Test
    void percentDiscountHundred() {
        DiscountPolicy policy = new PercentDiscount(100);           // 100%
        assertEquals(0.0, policy.apply(100.0), 0.0001);             // 100 → 0
    }

    // Отрицательный процент недопустим
    @Test
    void percentDiscountRejectsNegativePercent() {
        assertThrows(IllegalArgumentException.class,
                () -> new PercentDiscount(-1));                     // лямбда: исключение вылетит внутри
    }

    // Процент > 100 недопустим
    @Test
    void percentDiscountRejectsOverHundredPercent() {
        assertThrows(IllegalArgumentException.class,
                () -> new PercentDiscount(101));
    }

    // Отрицательная цена недопустима
    @Test
    void discountRejectsNegativePrice() {
        assertThrows(IllegalArgumentException.class,
                () -> new NoDiscount().apply(-10));
    }

    // ЗАМЕНА ПОЛИТИКИ: клиент работает с любой реализацией
    @Test
    void priceCalculatorCanSwitchPolicy() {
        PriceCalculator calc1 = new PriceCalculator(new NoDiscount());        // без скидки
        PriceCalculator calc2 = new PriceCalculator(new PercentDiscount(50)); // 50%
        assertEquals(100.0, calc1.calculate(100.0), 0.0001);                  // 100
        assertEquals(50.0, calc2.calculate(100.0), 0.0001);                   // 100 * 0.5
    }
}