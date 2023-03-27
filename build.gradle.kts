import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessExtensionPredeclare

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(libs.secretsGradlePlugin)
    }
}

plugins {
    id("com.android.application") version "7.4.2" apply false
    id("com.android.library") version "7.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("com.diffplug.spotless") version "6.17.0"
}

subprojects {

    afterEvaluate {
        apply(plugin = "com.diffplug.spotless")

        configure<SpotlessExtension> {
            kotlin {
                target("**/*.kt")
                targetExclude("$buildDir/**/*.kt", "bin/**/*.kt")
                ktlint(libs.versions.ktlint.get())
                trimTrailingWhitespace()
                endWithNewline()
            }

            format("misc") {
                target("**/*.kts", "**/*.gradle", "**/*.xml", "**/*.md", "**/.gitignore")
                targetExclude("**/build/**/*.kts", "**/build/**/*.xml")
                trimTrailingWhitespace()
                indentWithSpaces(4)
                endWithNewline()
            }
        }
    }
}

configure<SpotlessExtension> {
    predeclareDeps()
}

configure<SpotlessExtensionPredeclare> {
    kotlin {
        ktlint(libs.versions.ktlint.get())
    }
}
