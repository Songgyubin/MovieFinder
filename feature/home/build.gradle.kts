plugins {
    alias(libs.plugins.gyub.android.feature)
}

android {
    namespace = "com.gyub.feature.home"
}

dependencies {
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.common)
    implementation(libs.androidx.paging.compose)
}