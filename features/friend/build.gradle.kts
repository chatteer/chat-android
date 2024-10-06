import com.chatteer.chat.setNamespace

plugins {
    id("chatteer.android.feature")
    id("kotlinx-serialization")
}

android {
    setNamespace("features.friend")
    defaultConfig {
        testInstrumentationRunner =
            "com.chatteer.core.testing.runner.ChatteerHiltTestRunner"
    }
}

dependencies {
    implementation(projects.core)
    implementation(projects.testing)
    implementation(libs.retrofit)
    implementation(libs.kotlinx.json)
    implementation(libs.androidx.appcompat)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.coroutine.test)
    kspAndroidTest(libs.hilt.compiler)
}