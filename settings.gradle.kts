pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MovieFinder"
include(":app")

include(":core:data")
include(":core:domain")
include(":core:network")
include(":core:db")
include(":core:navigation")
include(":core:common")
include(":core:design")
include(":feature:home")
include(":feature:bookmark")
include(":feature:main")
include(":benchmarks")
