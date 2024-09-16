import com.chatteer.chat.setNamespace

plugins {
    id("chatteer.android.feature")
}

android {
    setNamespace("feature.main")
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}