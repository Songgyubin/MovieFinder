plugins {
    alias(libs.plugins.gyub.android.library)
    alias(libs.plugins.gyub.library.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.gyub.core.navigation"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}