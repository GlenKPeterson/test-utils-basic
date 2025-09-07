# test-utils-basic
Utilities for testing Utilities for testing common Java contracts: equals(), hashCode(), compare(), compareTo(), and serialization.
I find a bug almost every time I apply these tests to old code.  Usage is defined in the Javadocs.

Bill Venners gave me the idea of contract-based testing:
https://www.youtube.com/watch?v=bCTZQi2dpl8
Any bugs are my own.

## Maven Dependency
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.organicdesign/test-utils-basic/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.organicdesign/test-utils-basic)
[![javadoc](https://javadoc.io/badge2/org.organicdesign/test-utils-basic/javadoc.svg)](https://javadoc.io/doc/org.organicdesign/test-utils-basic)

Note that this project is just for testing, so add it only to the `test` scope of your project:

Maven:
```xml
<dependency>
	<groupId>org.organicdesign</groupId>
	<artifactId>test-utils-basic</artifactId>
	<version>0.0.1</version>
	<scope>test</scope>
</dependency>
```

Gradle .kts: `testImplementation("org.organicdesign:test-utils-basic:0.0.1")`

## Usage: Equality
```java
import static org.organicdesign.testUtils.EqualsContract.equalsDistinctHashCode;
import static org.organicdesign.testUtils.EqualsContract.equalsSameHashCode;

public class PaddingTest {
    @Test public void equalHashTest() {
        // Test padding-top different
        equalsDistinctHashCode(Padding.of(1),
                               Padding.of(1,1,1,1),
                               Padding.of(1),
                               Padding.of(2,1,1,1));

        // Test transposed padding-right and padding-bottom are different (but have same hashcode)
        equalsSameHashCode(Padding.of(3, 5, 7, 1.1f),
                           Padding.of(3, 5, 7, 1.1f),
                           Padding.of(3, 5, 7, 1.1f),
                           Padding.of(3, 7, 5, 1.1f));

        // Padding values that differ by less than 0.1f have the same hashcode
        // but are not equal.  Prove it (also tests when padding-left is different):
        equalsSameHashCode(Padding.of(1),
                           Padding.of(1, 1, 1, 1),
                           Padding.of(1),
                           Padding.of(1, 1, 1, 1.0001f));
    }
}
```

The above is a suitable test for the class [com.planbase.pdf.layoutmanager.Padding](https://github.com/GlenKPeterson/PdfLayoutManager/blob/master/src/main/java/com/planbase/pdf/layoutmanager/Padding.java)

* All four arguments must be distinct objects (not pointers to the same object in memory)
* The first three arguments must equal each other (and therefore must have the same hashCode), but must not equal the fourth argument.
* When possible/practical, use a fourth object with a different hashCode
* When practical, it's a good idea to also find and test an unequal fourth object with the same hashCode
* Think about the most different ways you can construct objects for the first three arguments.  The above example is a little weak in that regard because there just aren't many legal ways to construct Padding (good for Padding!).


## Contributions
To build locally (in an appropriate folder), you need Java 8+, gradle, and git installed.  Then:
```bash
git clone https://github.com/PlanBase/TestUtilsBasic.git
gradle clean assemble publishToMavenLocal
```

## Change Log

### 2.0.6 2022-05-27 "assertBetween"
- Added a range assertion method.

### 0.0.1 2022-04-28 "test-utils-basic"
 - Forked from [TestUtils](https://github.com/GlenKPeterson/TestUtils)
 - Name change.
 - Removed all Http stuff because it relied on [Indented](https://github.com/PlanBase/Indented)

## License
Copyright 2015 Glen Peterson and PlanBase Inc.

This program and the accompanying materials are made available under the
terms of the Apache License, Version 2.0:
https://www.apache.org/licenses/LICENSE-2.0
OR the Eclipse Public License v. 2.0:
https://www.eclipse.org/legal/epl-2.0

SPDX-License-Identifier: Apache-2.0 OR EPL-2.0