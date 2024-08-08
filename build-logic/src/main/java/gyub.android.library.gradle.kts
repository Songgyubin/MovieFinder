import com.gyub.build_logic.configureCoroutineAndroid
import com.gyub.build_logic.configureHiltAndroid
import com.gyub.build_logic.configureKotlinAndroid

plugins {
    id("com.android.library")
}

configureKotlinAndroid()
configureCoroutineAndroid()
configureHiltAndroid()
