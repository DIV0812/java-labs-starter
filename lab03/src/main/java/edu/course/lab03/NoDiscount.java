package edu.course.lab03;

/**
 * Политика без скидки.
 */
public final class NoDiscount implements DiscountPolicy {   // implements — реализуем интерфейс

    @Override                                                // @Override — переопределяем метод интерфейса
    public double apply(double price) {
        if (price < 0) {                                     // цена не может быть отрицательной
            throw new IllegalArgumentException("price must not be negative");
        }
        return price;                                        // без скидки — возвращаем как есть
    }
}