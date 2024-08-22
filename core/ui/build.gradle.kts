plugins {
    alias(libs.plugins.gyub.android.library)
    alias(libs.plugins.gyub.library.compose)
}

android {
    namespace = "com.gyub.core.ui"
}

dependencies {
    implementation(libs.androidx.lifecycle.runtimeCompose)
}