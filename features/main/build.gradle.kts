import com.chatteer.chat.setNamespace

plugins {
    id("chatteer.android.feature")
}

android {
    setNamespace("features.main")
}

dependencies {
    implementation(projects.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.kotlinx.immutable)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}