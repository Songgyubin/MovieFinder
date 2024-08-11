plugins {
    alias(libs.plugins.gyub.android.library)
    alias(libs.plugins.gyub.kotlin.hilt)
}

android {
    namespace = "com.gyub.core.domain"
}

dependencies {

    implementation(libs.androidx.paging.common)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
}