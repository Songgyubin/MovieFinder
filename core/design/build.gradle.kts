plugins {
    alias(libs.plugins.gyub.android.library)
    alias(libs.plugins.gyub.library.compose)
}

android {
    namespace = "com.gyub.core.design"
}

dependencies {

    implementation(libs.coil.compose)
    api(libs.backpack.design.system)
}