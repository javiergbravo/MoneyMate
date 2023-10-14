pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MoneyMate"
include(":androidApp")
include(":core")
include(":logger")
include(":feature:user")
include(":feature:dashboard")
