package edu.course.lab01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseToolkitTest {

    @Test
    void returnsTrueForEvenNumber() {
        assertTrue(CourseToolkit.isEven(8));
    }

    @Test
    void returnsFalseForOddNumber() {
        assertFalse(CourseToolkit.isEven(7));
    }

    @Test
    void returnsTrueForNegativeEvenNumber() {
        assertTrue(CourseToolkit.isEven(-8));
    }

    @Test
    void returnsFalseForNumbersLessThanTwo() {
        assertFalse(CourseToolkit.isPrime(-5));
        assertFalse(CourseToolkit.isPrime(0));
        assertFalse(CourseToolkit.isPrime(1));
    }

    @Test
    void returnsTrueForTwo() {
        assertTrue(CourseToolkit.isPrime(2));
    }

    @Test
    void returnsFalseForCompositeNumber() {
        assertFalse(CourseToolkit.isPrime(15));
    }

    @Test
    void returnsFalseForSquareOfPrime() {
        assertFalse(CourseToolkit.isPrime(49));
    }

    @Test
    void returnsTrueForLargePrime() {
        assertTrue(CourseToolkit.isPrime(97));
    }

    @Test
    void returnsTrueForSimplePalindrome() {
        assertTrue(CourseToolkit.isPalindrome("level"));
    }

    @Test
    void returnsFalseWhenCaseDiffers() {
        assertFalse(CourseToolkit.isPalindrome("Level"));
    }

    @Test
    void returnsFalseWhenSpacesDiffer() {
        assertFalse(CourseToolkit.isPalindrome("never odd or even"));
    }

    @Test
    void throwsForNullInIsPalindrome() {
        assertThrows(IllegalArgumentException.class,
                () -> CourseToolkit.isPalindrome(null));
    }

    @Test
    void returnsFractionalAverage() {
        assertEquals(2.5, CourseToolkit.average(new int[]{1, 2, 3, 4}), 0.0001);
    }

    @Test
    void handlesNegativeValues() {
        assertEquals(-2.0, CourseToolkit.average(new int[]{-1, -2, -3}), 0.0001);
    }

    @Test
    void doesNotModifyInputArray() {
        int[] input = {3, 1, 2};
        int[] copy = input.clone();
        CourseToolkit.average(input);
        assertArrayEquals(copy, input);
    }

    @Test
    void throwsForNullOrEmptyArray() {
        assertThrows(IllegalArgumentException.class,
                () -> CourseToolkit.average(null));
        assertThrows(IllegalArgumentException.class,
                () -> CourseToolkit.average(new int[0]));
    }
}