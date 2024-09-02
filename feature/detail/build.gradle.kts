plugins {
    alias(libs.plugins.gyub.android.feature)
}

android {
    namespace = "com.gyub.feature.detail"
}

dependencies {
    implementation(libs.kotlinx.immutable)
}