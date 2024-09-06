plugins {
    alias(libs.plugins.gyub.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.gyub.core.common"
}

dependencies {
    api(project(":core:model"))

    implementation(libs.kotlinx.serialization.json)
}