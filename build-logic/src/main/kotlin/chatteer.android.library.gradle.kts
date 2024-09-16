import com.chatteer.chat.configureHiltAndroid
import com.chatteer.chat.configureHiltKotlin
import com.chatteer.chat.configureKotlinAndroid

plugins {
    id("com.android.library")
}

configureKotlinAndroid()
configureHiltAndroid()
configureHiltKotlin()

