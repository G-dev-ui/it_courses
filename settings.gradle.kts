pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "courses"

include(
    ":app",
    ":core:common",
    ":core:ui",
    ":core:database",
    ":core:network",
    ":core:network-mock",
    ":feature:home",
    ":feature:course",
    ":feature:favorites",
    ":feature:profile",
)

