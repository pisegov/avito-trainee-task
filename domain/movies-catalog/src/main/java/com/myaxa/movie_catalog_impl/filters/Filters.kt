package com.myaxa.movie_catalog_impl.filters

import com.myaxa.movie_catalog_impl.filters.Filter.ListFilter
import com.myaxa.movie_catalog_impl.filters.Filter.RatingFilter
import com.myaxa.movie_catalog_impl.filters.Filter.YearFilter

val contentTypeNames = mapOf(
    "animated-series" to "Мультсериал",
    "anime" to "Аниме",
    "cartoon" to "Мультфильм",
    "movie" to "Фильм",
    "tv-series" to "Сериал",
)

data class Filters(
    val year: YearFilter = YearFilter(),
    val rating: RatingFilter = RatingFilter(),
    val types: ListFilter = ListFilter("Тип контента"),
    val countries: ListFilter = ListFilter("Страны производства"),
    val genres: ListFilter = ListFilter("Жанры"),
    val ageRatings: ListFilter = defaultAgeRatingsFilter,
    val networks: ListFilter = defaultNetworksFilter,
) {
    companion object {
        val defaultAgeRatingsFilter
            get() = ListFilter("Возрастной рейтинг", listOf("0", "6", "12", "18")
                .associateWith { FilterValue("$it+", false) })
        val defaultNetworksFilter
            get() = ListFilter("Сеть производства", listOf("Netflix", "HBO", "Кинопоиск", "START", "Wink")
                .associateWith { FilterValue(it, false) })
    }

    val isSelected get() = map.entries.find { it.value.isSelected } != null

    val map = mapOf(
        FilterType.YEAR to year,
        FilterType.RATING to rating,
        FilterType.CONTENT_TYPES to types,
        FilterType.COUNTRIES to countries,
        FilterType.GENRES to genres,
        FilterType.AGE_RATINGS to ageRatings,
        FilterType.NETWORKS to networks,
    )

    fun clearedCopy(): Filters {
        return copy(
            year = year.clearedSelectionCopy(),
            rating = rating.clearedSelectionCopy(),
            types = types.clearedSelectionCopy(),
            countries = countries.clearedSelectionCopy(),
            genres = genres.clearedSelectionCopy(),
            ageRatings = ageRatings.clearedSelectionCopy(),
            networks = networks.clearedSelectionCopy(),
        )
    }

    fun copyWithReplacedFilter(type: FilterType, filter: Filter): Filters {
        val newMap = map.toMutableMap()
        newMap[type] = filter
        return copy(
            year = newMap[FilterType.YEAR] as YearFilter,
            rating = newMap[FilterType.RATING] as RatingFilter,
            types = newMap[FilterType.CONTENT_TYPES] as ListFilter,
            countries = newMap[FilterType.COUNTRIES] as ListFilter,
            genres = newMap[FilterType.GENRES] as ListFilter,
            ageRatings = newMap[FilterType.AGE_RATINGS] as ListFilter,
            networks = newMap[FilterType.NETWORKS] as ListFilter,
        )
    }
}
