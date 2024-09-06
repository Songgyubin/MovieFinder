plugins {
    alias(libs.plugins.gyub.android.library)
    alias(libs.plugins.gyub.kotlin.hilt)
}

android {
    namespace = "com.gyub.core.data"
}

dependencies {
    api(project(":core:model"))

    implementation(project(":core:network"))
    implementation(project(":core:db"))
    implementation(project(":core:domain"))
    implementation(project(":core:common"))

    implementation(libs.androidx.paging.common)
    implementation(libs.androidx.room.paging)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.coroutines.test)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}