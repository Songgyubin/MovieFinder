import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

group = "com.gyub.apps.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.androidx.room.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("ComposeAndroidPlugin") {
            id = "gyub.plugin.compose.android"
            implementationClass = "ComposeAndroidConventionPlugin"
        }

        register("ComposeLibraryPlugin") {
            id = "gyub.plugin.compose.library"
            implementationClass = "ComposeLibraryConventionPlugin"
        }

        register("ApplicationAndroidPlugin") {
            id = "gyub.plugin.application.android"
            implementationClass = "ApplicationAndroidConventionPlugin"
        }

        register("LibraryAndroidPlugin") {
            id = "gyub.plugin.library.android"
            implementationClass = "LibraryAndroidConventionPlugin"
        }

        register("HiltAndroidPlugin") {
            id = "gyub.plugin.hilt.android"
            implementationClass = "HiltAndroidConventionPlugin"
        }

        register("HiltKotlinPlugin") {
            id = "gyub.plugin.hilt.kotlin"
            implementationClass = "HiltKotlinConventionPlugin"
        }

        register("FeatureAndroidPlugin") {
            id = "gyub.plugin.feature.android"
            implementationClass = "FeatureAndroidConventionPlugin"
        }
    }
}