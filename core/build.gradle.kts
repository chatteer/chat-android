import com.chatteer.chat.setNamespace
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("chatteer.android.library")
    id("chatteer.android.hilt")
    id("chatteer.android.compose")
    id("kotlinx-serialization")
}

val properties = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "local.properties")))
}

android {
    setNamespace("core")
    buildTypes {
        debug {
            resValue("string", "BaseUrl", properties.getProperty("base_url"))
            resValue("string","test_token",properties.getProperty("test_token"))
        }
        release {
            resValue("string", "BaseUrl", "https://www.naver.com")
        }
    }
}

dependencies {
    implementation(libs.httptracking.interceptor)
    implementation(libs.androidx.core.ktx)
    implementation(libs.coroutine)
    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofit.json)
    implementation(libs.kotlinx.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.glide.compose)
    implementation(libs.androidx.appcompat)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}