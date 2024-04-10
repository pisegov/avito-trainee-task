pluginManagement {
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

rootProject.name = "Avito-kinopoisk-test"
include(":app")
include(":movies-api")
include(":database")
include(":features:movies-catalog")
include(":movies-data")
include(":common")
include(":features:movie-details-impl")
include(":features:movie-details-api")
include(":domain:movie-details")
include(":data:movie-details")
