package edu.course.lab02;

/**
 * Банковский счёт с проверкой инварианта: баланс не может быть отрицательным.
 */
public final class BankAccount {  // final — от класса нельзя наследоваться

    private int balance;          // приватное поле: снаружи не видно

    // Конструктор: создаёт счёт с проверкой начального баланса
    public BankAccount(int initialBalance) {
        if (initialBalance < 0) {                                   // запрещаем отрицательный старт
            throw new IllegalArgumentException("initialBalance must not be negative");
        }
        this.balance = initialBalance;                              // сохраняем в поле через this
    }

    // Пополнение счёта
    public void deposit(int amount) {
        if (amount <= 0) {                                          // только положительная сумма
            throw new IllegalArgumentException("amount must be positive");
        }
        this.balance += amount;                                     // увеличиваем баланс
    }

    // Списание со счёта
    public void withdraw(int amount) {
        if (amount <= 0) {                                          // только положительная сумма
            throw new IllegalArgumentException("amount must be positive");
        }
        if (amount > this.balance) {                                // нельзя снять больше, чем есть
            throw new IllegalStateException("insufficient funds");
        }
        this.balance -= amount;                                     // уменьшаем баланс
    }

    // Геттер — единственный способ узнать баланс. Setter'а нет.
    public int getBalance() {
        return balance;
    }
}