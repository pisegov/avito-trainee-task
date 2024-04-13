package com.myaxa.movies_catalog

import com.myaxa.movies_catalog.Filter.ListFilter
import com.myaxa.movies_catalog.Filter.RatingFilter
import com.myaxa.movies_catalog.Filter.YearFilter

sealed interface Filter {
    val title: String
    val isSelected: Boolean

    fun clearedSelectionCopy(): Filter

    data class YearFilter(
        override val title: String = "Год выхода",
        val from: String? = null,
        val to: String? = null,
    ) : Filter {
        override val isSelected: Boolean
            get() = from != null || to != null

        override fun toString(): String {
            return when {
                from != null && to != null && from != to ->
                    "$from-$to"

                (from == to && from != null) || (from != null) ->
                    "$from"

                (to != null) ->
                    "$to"

                else -> {
                    ""
                }
            }
        }

        override fun clearedSelectionCopy() = copy(from = null, to = null)
    }

    data class RatingFilter(
        override val title: String = "Рейтинг на Кинопоиске",
        val from: String? = null,
    ) : Filter {
        override val isSelected: Boolean
            get() = from != null

        override fun toString(): String {
            return from?.let { from.toString() } ?: ""
        }

        override fun clearedSelectionCopy() = copy(from = null)
    }

    data class ListFilter(
        override val title: String,
        val options: Map<String, Boolean> = emptyMap(),
    ) : Filter {
        override val isSelected: Boolean
            get() = options.entries.find { it.value } != null

        override fun clearedSelectionCopy(): ListFilter {
            val newOptions = this.options.keys.associateWith { false }
            return copy(options = newOptions)
        }

        fun selectedOptions(): List<String> {
            return options.entries.filter { it.value }.map { it.key }
        }
    }
}

enum class FilterType {
    YEAR,
    RATING,
    CONTENT_TYPES,
    COUNTRIES,
    GENRES,
    AGE_RATINGS,
    NETWORKS
}

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
            get() = ListFilter("Возрастной рейтинг", listOf("0", "6", "12", "18").associateWith { false })
        val defaultNetworksFilter
            get() = ListFilter("Сеть производства", listOf("Netflix", "Amazon", "HBO").associateWith { false })
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
