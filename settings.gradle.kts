pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Dictionary"
include(":app")
include(":libraries:room-lib")
include(":libraries:ui-base-lib")
include(":libraries:utils-lib")
include(":features:base")
include(":features:home")
include(":libraries:data-lib")
include(":libraries:context-provider-lib")
