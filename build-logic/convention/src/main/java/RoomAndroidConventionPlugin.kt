import com.gyub.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Room 플러그인 컨벤션
 *
 * @author   Gyub
 * @created  2024/08/10
 */
class RoomAndroidConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }

            dependencies {
                add("implementation", libs.findLibrary("androidx.room.runtime").get())
                add("implementation", libs.findLibrary("androidx.room.ktx").get())
                add("ksp", libs.findLibrary("androidx.room.compiler").get())
            }
        }
    }
}