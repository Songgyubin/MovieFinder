plugins {
    alias(libs.plugins.gyub.android.library)
    alias(libs.plugins.gyub.kotlin.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.gyub.core.data_test"
}

dependencies {
    api(project(":core:data"))

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.hilt.android.testing)
    implementation(libs.androidx.paging.common)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.coroutines.test)
}