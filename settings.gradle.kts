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

// core
include(
    ":core:data",
    ":core:domain",
    ":core:network",
    ":core:db",
    ":core:navigation",
    ":core:common",
    ":core:design",
    ":core:testing",
    ":core:ui"
)

// feature
include(
    ":feature:home",
    ":feature:bookmark",
    ":feature:main",
    ":feature:detail"
    )

include(":benchmarks")
include(":ui-test-hilt-manifest")
include(":core:model")
