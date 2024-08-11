plugins {
    alias(libs.plugins.gyub.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.gyub.core.common"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}