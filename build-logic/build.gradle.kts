plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidHilt") {
            id = "gyub.android.hilt"
            implementationClass = "com.gyub.build_logic.HiltAndroidPlugin"
        }
        register("kotlinHilt") {
            id = "gyub.kotlin.hilt"
            implementationClass = "com.gyub.build_logic.HiltKotlinPlugin"
        }
    }
}
