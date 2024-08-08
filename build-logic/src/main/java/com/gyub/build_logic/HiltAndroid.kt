package com.gyub.build_logic

import com.gyub.build_logic.base.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Hilt 세팅
 *
 * @author   Gyub
 * @created  2024/08/08
 */
internal fun Project.configureHiltAndroid() {
    with(pluginManager) {
        apply("dagger.hilt.android.plugin")
        apply("com.google.devtools.ksp")
    }

    val libs = extensions.libs
    dependencies {
        "implementation"(libs.findLibrary("hilt.android").get())
        "ksp"(libs.findLibrary("hilt.android.compiler").get())
        "kspAndroidTest"(libs.findLibrary("hilt.android.compiler").get())
    }
}

internal class HiltAndroidPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            configureHiltAndroid()
        }
    }
}