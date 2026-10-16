package edu.course.lab03;

/**
 * Интерфейс скидочной политики.
 * Любая реализация обязана иметь метод apply(double).
 */
public interface DiscountPolicy {          // interface — контракт без реализации
    double apply(double price);            // метод без тела: реализация решает сама
}