plugins {
    alias(libs.plugins.gyub.android.library)
    alias(libs.plugins.gyub.kotlin.hilt)
}

android {
    namespace = "com.gyub.core.data"
}

dependencies {
    implementation(project(":core:network"))
    implementation(project(":core:db"))
    implementation(project(":core:domain"))

    implementation(libs.androidx.paging.common)
    implementation(libs.androidx.room.paging)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.coroutines.test)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}