package com.chatteer.chat

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinAndroid() {
    // Plugins
    pluginManager.apply("org.jetbrains.kotlin.android")

    // Android settings
    androidExtension.apply {
        compileSdk = 34

        defaultConfig {
            minSdk = 28
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
            // isCoreLibraryDesugaringEnabled = true
        }

        buildTypes {
            getByName("debug") {
                isMinifyEnabled = false
                resValue("string","BuildType","debug")
            }
            getByName("release") {
                isMinifyEnabled = false
                isShrinkResources = false
                resValue("string","BuildType","release")
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
        testOptions {
            unitTests {
                isIncludeAndroidResources = true
            }
        }

        val libs = extensions.libs
        dependencies {
            add("implementation", libs.findLibrary("timber").get())
        }
    }

    configureKotlin()
}

internal fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            // Treat all Kotlin warnings as errors (disabled by default)
            // Override by setting warningsAsErrors=true in your ~/.gradle/gradle.properties
            val warningsAsErrors: String? by project
            allWarningsAsErrors.set(warningsAsErrors.toBoolean())
            freeCompilerArgs.set(
                freeCompilerArgs.get() + listOf(
                    "-opt-in=kotlin.RequiresOptIn",
                )
            )
        }
    }
}