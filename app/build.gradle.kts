plugins {
    id("chatteer.android.application")
}

android {
    namespace = "com.chatteer.chat"

    defaultConfig {
        applicationId = "com.chatteer.chat"
        versionCode = 1
        versionName = "0.0.1"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(projects.features.main)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.httptracking.ui)
    implementation(libs.httptracking.interceptor)
}