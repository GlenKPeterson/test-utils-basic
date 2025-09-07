import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Deploying to OSSRH with Gradle
// https://central.sonatype.org/pages/gradle.html
// https://github.com/gradle-nexus/publish-plugin

// Did you update version number here AND in the README?
// This is different from other projects because it is TEST SCOPED.

// To find out if any dependencies need upgrades:
// gradle --refresh-dependencies dependencyUpdates

// To publish to maven local:
// gradle --warning-mode all clean assemble dokkaJavadocJar publish publishToMavenLocal

// To publish to Sonatype (do the maven local above first):
// gradle --warning-mode all clean test assemble dokkaJavadocJar publish publishToSonatype closeAndReleaseSonatypeStagingRepository

// If half-deployed, sign in here:
// https://oss.sonatype.org
// Click on "Staging Repositories"
// Open the "Content" for the latest one you uploaded.
// If it looks good, "Close" it and wait.
// When it's really "closed" with no errors, "Release" (and automatically drop) it.
//
// Alternatively, if you can see it here, then it's ready to be "Closed" and deployed manually:
// https://oss.sonatype.org/content/groups/staging/org/organicdesign/TestUtilsBasic/
// Here once released:
// https://repo1.maven.org/maven2/org/organicdesign/TestUtilsBasic/

// https://docs.gradle.org/current/userguide/build_environment.html
// You must have the following set in ~/.gradle/gradle.properties
// sonatypeUsername=
// sonatypePassword=
//
// At least while dokka crashes the gradle daemon you also want:
// org.gradle.daemon=false
// Or run with --no-daemon
val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
println("Library aliases: ${versionCatalog.libraryAliases}")

plugins {
    `java-library`
    `maven-publish`
//    signing
    alias(libs.plugins.m.versions)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.dokka.javadoc)
////    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

dependencies {
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}

group = "com.planbase"
// Remember to update the version number in both the Maven and Gradle imports in README.md
// Version is now set from the master project.
// version = "0.0.2"
description = "Utilities for testing common Java contracts: equals(), hashCode(), compare(), compareTo(), and serialization"

java {
    withJavadocJar()
    withSourcesJar()
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            afterEvaluate {
                artifactId = tasks.jar.get().archiveBaseName.get()
            }
//            artifact(tasks["dokkaJavadocJar"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set(rootProject.name)
                packaging = "jar"
                description.set(project.description)
                url.set("https://github.com/PlanBase/TestUtilsBasic")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://apache.org/licenses/LICENSE-2.0.txt")
                    }
                    license {
                        name.set("The Eclipse Public License v. 2.0")
                        url.set("https://eclipse.org/legal/epl-2.0")
                    }
                }
                developers {
                    developer {
                        id.set("PlanBase")
                        name.set("Glen K. Peterson")
                        organization.set("PlanBase Inc.")
                        url.set("https://github.com/PlanBase/test-utils-basic")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/PlanBase/TestUtilsBasic.git")
                    developerConnection.set("scm:git:https://github.com/PlanBase/TestUtilsBasic.git")
                    url.set("https://github.com/PlanBase/TestUtilsBasic.git")
                }
            }
        }
    }
}

//nexusPublishing {
//    repositories {
//        sonatype()
//    }
//}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}

//signing {
//    useGpgCmd()
//    sign(publishing.publications["mavenJava"])
//}

tasks.compileJava {
    options.encoding = "UTF-8"
}
repositories {
    mavenLocal()
    mavenCentral()
    maven(url="https://jitpack.io")
}