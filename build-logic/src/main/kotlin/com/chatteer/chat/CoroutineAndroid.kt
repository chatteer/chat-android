package com.chatteer.chat

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureCoroutineAndroid() {
    val libs = extensions.libs
    configureCoroutineKotlin()
    dependencies {
        "implementation"(libs.findLibrary("coroutine.android").get())
    }
}

internal fun Project.configureCoroutineKotlin() {
    val libs = extensions.libs
    dependencies {
        "implementation"(libs.findLibrary("coroutine").get())
        "testImplementation"(libs.findLibrary("coroutine.test").get())
    }
}
