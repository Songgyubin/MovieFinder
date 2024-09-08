plugins {
    alias(libs.plugins.gyub.android.library)
    alias(libs.plugins.gyub.library.compose)
}

android {
    namespace = "com.gyub.core.ui"
}

dependencies {
    implementation(project(":core:design"))
    implementation(project(":core:model"))
    
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.kotlinx.datetime)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test)
    testImplementation (libs.mockk)
}