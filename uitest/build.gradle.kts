import com.chatteer.chat.setNamespace

plugins {
    id("chatteer.android.library")
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