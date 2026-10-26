package edu.course.lab03;

/**
 * Калькулятор цены. Зависит от интерфейса, а не от конкретной политики.
 */
public final class PriceCalculator {

    private final DiscountPolicy policy;      // поле ИНТЕРФЕЙСНОГО типа

    // Политика передаётся снаружи. Внутри не создаётся.
    public PriceCalculator(DiscountPolicy policy) {
        if (policy == null) {
            throw new IllegalArgumentException("policy must not be null");
        }
        this.policy = policy;
    }

    // Клиент не знает, какая именно политика внутри — вызывает apply через интерфейс
    public double calculate(double price) {
        return policy.apply(price);            // полиморфизм: вызовется метод фактического типа
    }
}