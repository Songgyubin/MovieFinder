plugins {
    alias(libs.plugins.gyub.android.feature)
}

android {
    namespace = "com.gyub.feature.main"

    defaultConfig {
        testInstrumentationRunner = "com.gyub.core.testing.runner.MovieFinderTestRunner"
    }
}

dependencies {
    implementation(project(":feature:home"))
    implementation(project(":feature:bookmark"))
    implementation(project(":feature:detail"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.kotlinx.immutable)
    implementation(libs.androidx.navigation.testing)

    debugImplementation(project(":ui-test-hilt-manifest"))

    androidTestImplementation(libs.hilt.android.testing)
}