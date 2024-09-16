import com.chatteer.chat.configureCoroutineAndroid
import com.chatteer.chat.configureHiltAndroid

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