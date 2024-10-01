import com.chatteer.chat.configureCoroutineAndroid
import com.chatteer.chat.configureHiltAndroid
import com.chatteer.chat.libs

plugins {
    id("chatteer.android.library")
    id("chatteer.android.compose")
}

android {
    packaging {
        resources {
            excludes.add("META-INF/**")
        }
    }
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

configureHiltAndroid()
configureCoroutineAndroid()

dependencies {
    val libs = project.extensions.libs
    implementation(libs.findLibrary("hilt.navigation.compose").get())
}
