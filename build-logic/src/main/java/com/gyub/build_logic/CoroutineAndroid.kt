package com.gyub.build_logic

import com.gyub.build_logic.base.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * 코루틴 세팅
 *
 * @author   Gyub
 * @created  2024/08/08
 */
internal fun Project.configureCoroutineAndroid() {
    val libs = extensions.libs
    configureCoroutineKotlin()
    dependencies {
        "implementation"(libs.findLibrary("coroutines.android").get())
    }
}

internal fun Project.configureCoroutineKotlin() {
    val libs = extensions.libs
    dependencies {
        "implementation"(libs.findLibrary("coroutines.core").get())
        "testImplementation"(libs.findLibrary("coroutines.test").get())
    }
}
