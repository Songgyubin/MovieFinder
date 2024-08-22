plugins {
    alias(libs.plugins.gyub.android.library)
    alias(libs.plugins.gyub.android.hilt)
    alias(libs.plugins.gyub.room)
}

android {
    namespace = "com.gyub.core.db"
}

dependencies {
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.coroutines.test)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.kotlin.test)
    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.androidx.espresso.core)
}