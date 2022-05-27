package org.organicdesign.testUtils

object Assertions {
    @JvmStatic
    fun assertBetween(
        min: Long,
        size: Long,
        max: Long
    ) {
        if (max <= min) {
            throw IllegalArgumentException("Minimum value must be < maximum value")
        }
        if (min == Long.MIN_VALUE) {
            throw IllegalArgumentException("Invalid minimum value: nothing can be less than Long.MIN_VALUE")
        }
        if (max == Long.MAX_VALUE) {
            throw IllegalArgumentException("Invalid maximum value: nothing can be greater than Long.MAX_VALUE")
        }
        if (size < min ||
            size > max) {
            throw AssertionError("Expected value to be in the range of $min..$max but was $size")
        }
    }
}