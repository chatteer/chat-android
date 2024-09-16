package com.chatteer.chat

import org.gradle.api.Project

fun Project.setNamespace(name: String) {
    androidExtension.apply {
        namespace = "com.chatteer.$name"
    }
}