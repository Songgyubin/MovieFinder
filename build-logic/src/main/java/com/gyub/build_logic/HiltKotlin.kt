package com.gyub.build_logic

import com.gyub.build_logic.base.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Hilt 코틀린 세팅
 *
 * @author   Gyub
 * @created  2024/08/08
 */
internal fun Project.configureHiltKotlin() {
    with(pluginManager) {
        apply("com.google.devtools.ksp")
    }

    val libs = extensions.libs
    dependencies {
        "implementation"(libs.findLibrary("hilt.core").get())
        "ksp"(libs.findLibrary("hilt.compiler").get())
    }
}

internal class HiltKotlinPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            configureHiltKotlin()
        }
    }
}
