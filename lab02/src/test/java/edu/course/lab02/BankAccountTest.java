package edu.course.lab02;          // тот же пакет, что и код

import org.junit.jupiter.api.Test;  // аннотация @Test

import static org.junit.jupiter.api.Assertions.assertEquals;  // статический импорт
import static org.junit.jupiter.api.Assertions.assertThrows;  // статический импорт

class BankAccountTest {

    // Списание уменьшает баланс: 100 − 30 = 70
    @Test
    void withdrawDecreasesBalance() {
        BankAccount account = new BankAccount(100);                 // Arrange: счёт на 100
        account.withdraw(30);                                       // Act: списываем 30
        assertEquals(70, account.getBalance());                     // Assert: ожидаем 70
    }

    // Списание больше баланса → IllegalStateException
    @Test
    void withdrawThrowsWhenAmountExceedsBalance() {
        BankAccount account = new BankAccount(50);                  // Arrange: 50
        assertThrows(IllegalStateException.class,                   // ожидаем исключение
                () -> account.withdraw(51));                        // лямбда: списание 51
    }

    // Конструктор не принимает отрицательный баланс
    @Test
    void constructorRejectsNegativeBalance() {
        assertThrows(IllegalArgumentException.class,
                () -> new BankAccount(-1));                         // −1 → исключение
    }

    // Пополнение увеличивает баланс: 0 + 100 = 100
    @Test
    void depositIncreasesBalance() {
        BankAccount account = new BankAccount(0);                   // Arrange: 0
        account.deposit(100);                                       // Act: +100
        assertEquals(100, account.getBalance());                    // Assert: 100
    }

    // deposit с не положительным числом → исключение
    @Test
    void depositRejectsNonPositiveAmount() {
        BankAccount account = new BankAccount(100);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(0));   // 0 → искл.
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-5));  // −5 → искл.
    }
}