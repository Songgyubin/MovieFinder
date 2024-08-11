plugins {
    alias(libs.plugins.gyub.android.library)
    alias(libs.plugins.gyub.android.hilt)
}

android {
    namespace = "com.gyub.core.db"
}

dependencies {
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.paging)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.coroutines.test)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.kotlin.test)
    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.androidx.espresso.core)
}