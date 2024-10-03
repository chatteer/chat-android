import com.chatteer.chat.setNamespace

plugins {
    id("chatteer.android.library")
    id("kotlinx-serialization")
}

android {
    setNamespace("core.model")
}

dependencies {
    api(libs.kotlinx.datetime)
    implementation(libs.kotlinx.json)
}