plugins {
    alias(libs.plugins.gyub.android.application)
    alias(libs.plugins.gyub.android.hilt)
    alias(libs.plugins.android.application)
    alias(libs.plugins.baselineprofile)
}

android {
    namespace = "com.gyub.moviefinder"

    defaultConfig {
        applicationId = "com.gyub.moviefinder"
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(project(":core:design"))
    implementation(project(":feature:main"))
    implementation(libs.androidx.profileinstaller)
    "baselineProfile"(project(":benchmarks"))
}