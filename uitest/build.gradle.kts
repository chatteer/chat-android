import com.chatteer.chat.setNamespace

plugins {
    id("chatteer.android.library")
    id("chatteer.android.hilt")
    id("chatteer.android.compose")
    id("kotlinx-serialization")
}

android {
    setNamespace("core.uitest")
}

dependencies {
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}