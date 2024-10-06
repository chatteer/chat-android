import com.chatteer.chat.setNamespace

plugins {
    id("chatteer.android.library")
}

android {
    setNamespace("core.testing")

}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.hilt.android.testing)
    implementation(libs.compose.ui.test)
    implementation(libs.androidx.test.runner)
}