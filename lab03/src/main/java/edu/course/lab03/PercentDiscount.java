package edu.course.lab03;

/**
 * Политика процентной скидки. Процент — от 0 до 100.
 */
public final class PercentDiscount implements DiscountPolicy {

    private final double percent;      // неизменяемое поле: процент задаётся один раз

    // Конструктор: проверяем диапазон
    public PercentDiscount(double percent) {
        if (percent < 0 || percent > 100) {                      // вне [0,100] — невалидно
            throw new IllegalArgumentException("percent must be in [0, 100]");
        }
        this.percent = percent;
    }

    @Override
    public double apply(double price) {
        if (price < 0) {                                         // цена не отрицательная
            throw new IllegalArgumentException("price must not be negative");
        }
        return price * (1 - percent / 100.0);                    // 100 при 20% → 100 * 0.8 = 80
    }
}
