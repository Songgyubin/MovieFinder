plugins {
    alias(libs.plugins.gyub.android.feature)
}

android {
    namespace = "com.gyub.feature.home"
}

dependencies {
    implementation(project(":feature:detail"))

    implementation(libs.kotlinx.immutable)
}