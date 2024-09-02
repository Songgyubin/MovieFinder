plugins {
    alias(libs.plugins.gyub.android.library)
}

android {
    namespace = "com.gyub.core.testing"
}


dependencies {
    api(libs.junit)
    api(libs.kotlin.test)
    api(libs.coroutines.test)
    api(libs.mockk)
    api(libs.turbine)

    implementation(libs.androidx.runner)
    implementation(libs.hilt.android.testing)
}