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
include(":common")

include(":features:movie-catalog-api")
include(":features:movie-catalog-impl")
include(":features:movie-details-api")
include(":features:movie-details-impl")
include(":features:filters-bottomsheet-api")
include(":features:filters-bottomsheet-impl")

include(":domain:movies-catalog")
include(":domain:movie-details")

include(":data:movies-data")
include(":data:movie-details-data")
include(":data:movies-remote")
include(":data:database")
include(":data:network")
include(":data:mappers")
include(":data:movie-details-remote")
