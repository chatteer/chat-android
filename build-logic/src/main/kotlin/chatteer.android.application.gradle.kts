import com.chatteer.chat.configureHiltAndroid
import com.chatteer.chat.configureKotlinAndroid

plugins {
    id("com.android.application")
}

configureKotlinAndroid()
configureHiltAndroid()