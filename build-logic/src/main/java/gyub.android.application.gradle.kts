import com.gyub.build_logic.configureKotlinAndroid
import com.gyub.build_logic.configureHiltAndroid

plugins {
    id("com.android.application")
}

configureKotlinAndroid()
configureHiltAndroid()
