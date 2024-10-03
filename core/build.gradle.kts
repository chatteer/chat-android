import com.chatteer.chat.setNamespace

plugins {
    id("chatteer.android.library")
    id("chatteer.android.hilt")
    id("chatteer.android.compose")
    id("kotlinx-serialization")
}

android {
    setNamespace("core")
    buildTypes {
        debug {
            resValue("string", "BaseUrl", "https://www.naver.com")
        }
        release {
            resValue("string", "BaseUrl", "https://www.naver.com")
        }
    }
}

dependencies {
    implementation(projects.model)
    implementation(libs.httptracking.interceptor)
    implementation(libs.androidx.core.ktx)
    implementation(libs.coroutine)
    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofit.json)
    implementation(libs.kotlinx.json)
    implementation(libs.glide.compose)
    implementation(libs.androidx.appcompat)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}