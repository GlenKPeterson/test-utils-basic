// Copyright 2015-07-03 PlanBase Inc. & Glen Peterson
// SPDX-License-Identifier: Apache-2.0 OR EPL-2.0
package org.organicdesign.testUtils;

/**
 * Do Not Use Outside This Project.
 * It tests for some things that Java's type system lets you do.
 * Kotlin's type system doesn't allow this test because it would be nonsensical in Kotlin.
 * We have to keep this in Java (for now) to test for bugs in non-Kotlin code.
 */
enum BreakNullSafety {
    INSTANCE;
    @SuppressWarnings({"ResultOfMethodCallIgnored", "ConstantConditions"})
    void compareToNull(Comparable<?> comp) {
        comp.compareTo(null);
    }
}
