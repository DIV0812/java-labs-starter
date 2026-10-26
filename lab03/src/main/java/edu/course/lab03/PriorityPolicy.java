package edu.course.lab03;

/**
 * Интерфейс политики приоритета.
 */
public interface PriorityPolicy {                       // контракт
    int calculatePriority(ProjectTask task);            // возвращает числовой приоритет
}