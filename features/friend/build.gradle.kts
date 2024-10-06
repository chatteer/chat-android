import com.chatteer.chat.setNamespace

plugins {
    id("chatteer.android.feature")
    id("kotlinx-serialization")
}

android {
    setNamespace("features.friend")
}

dependencies {
    implementation(projects.core)
    implementation(libs.retrofit)
    implementation(libs.kotlinx.json)
    implementation(libs.androidx.appcompat)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}