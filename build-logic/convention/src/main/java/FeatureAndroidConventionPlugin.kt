import com.android.build.gradle.LibraryExtension
import com.gyub.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * Feature 모듈 플러그인 컨벤션
 *
 * @author   Gyub
 * @created  2024/08/11
 */
class FeatureAndroidConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("gyub.plugin.compose.library")
                apply("gyub.plugin.hilt.android")
                apply("gyub.plugin.library.android")
            }

            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
                packaging {
                    resources {
                        excludes.add("META-INF/**")
                    }
                }
            }

            dependencies {
                add("implementation", project(":core:design"))
                add("implementation", project(":core:navigation"))
                add("implementation", project(":core:domain"))
                add("implementation", project(":core:data"))
                add("implementation", project(":core:common"))
                add("implementation", project(":core:testing"))
                add("implementation", project(":core:ui"))

                add("implementation", libs.findLibrary("hilt.navigation.compose").get())
                add("implementation", libs.findLibrary("androidx.compose.navigation").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())

                add("testImplementation", libs.findLibrary("junit").get())
                add("testImplementation", libs.findLibrary("kotlin.test").get())
                add("testImplementation", libs.findLibrary("coroutines.test").get())
                add("testImplementation", libs.findLibrary("mockk").get())
                add("testImplementation", libs.findLibrary("turbine").get())

                add("androidTestImplementation", libs.findLibrary("androidx.compose.navigation.test").get())
            }
        }
    }
}