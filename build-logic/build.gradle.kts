plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidHilt") {
            id = "chat.android.hilt"
            implementationClass = "com.chatteer.chat.HiltAndroidPlugin"
        }
        register("kotlinHilt") {
            id = "chat.kotlin.hilt"
            implementationClass = "com.chatteer.chat.HiltKotlinPlugin"
        }
    }
}
