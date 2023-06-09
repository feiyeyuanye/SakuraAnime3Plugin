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
rootProject.name = "SakuraAnime3Plugin"
include(":app")
val pluginApi = ":MediaBoxPluginApi"
include(pluginApi)
project(pluginApi).projectDir = File("./submodules/MediaBoxPlugin/pluginApi")