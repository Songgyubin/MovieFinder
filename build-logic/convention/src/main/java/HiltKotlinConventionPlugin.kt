import com.gyub.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Hilt Kotlin 컨벤션 플러그인
 *
 * @author   Gyub
 * @created  2024/08/10
 */
class HiltKotlinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }

            dependencies {
                add("implementation",libs.findLibrary("hilt.core").get())
                add("ksp", libs.findLibrary("hilt.compiler").get())
            }
        }
    }
}