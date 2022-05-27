package org.organicdesign.testUtils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.organicdesign.testUtils.Assertions.assertBetween

internal class AssertionsTest {
    @Test
    fun testAssertInRange() {
        var err: Throwable

        err = assertThrowsExactly(IllegalArgumentException::class.java) {
            assertBetween(6, 2, 5)
        }
        assertEquals("Minimum value must be < maximum value",
                     err.message)

        err = assertThrowsExactly(IllegalArgumentException::class.java) {
            assertBetween(Long.MIN_VALUE, 2, 5)
        }
        assertEquals("Invalid minimum value: nothing can be less than Long.MIN_VALUE",
                     err.message)

        err = assertThrowsExactly(IllegalArgumentException::class.java) {
            assertBetween(1, 2,  Long.MAX_VALUE)
        }
        assertEquals("Invalid maximum value: nothing can be greater than Long.MAX_VALUE",
                     err.message)

        err = assertThrowsExactly(AssertionError::class.java) {
            assertBetween(3, 2, 5)
        }
        assertEquals("Expected value to be in the range of 3..5 but was 2",
                     err.message)

        err = assertThrowsExactly(AssertionError::class.java) {
            assertBetween(3, 6, 5)
        }
        assertEquals("Expected value to be in the range of 3..5 but was 6",
                     err.message)

        assertBetween(1, 2, 3)
        assertBetween(-3, -2, -1)
        assertBetween(-1, 0, 1)
    }
}