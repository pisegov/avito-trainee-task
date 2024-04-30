package com.myaxa.movies_data

import com.myaxa.movie_catalog_impl.filters.Filters
import com.myaxa.movies.database.models.DbRequestFilters
import com.myaxa.movies_api.models.NetworkRequestFilters

internal fun Filters?.toNetworkRequestFilters() = NetworkRequestFilters(
    year = if (this?.year?.isSelected == true) this.year.toString() else null,
    rating = if (this?.rating?.isSelected == true) this.rating.toString() else null,
    countries = this?.countries?.selectedOptions(),
    types = this?.types?.selectedOptions(),
    networks = this?.networks?.selectedOptions(),
    genres = this?.genres?.selectedOptions(),
    ageRatings = this?.ageRatings?.selectedOptions(),
)

internal fun Filters?.toDbRequestFilters(): DbRequestFilters {

    val yearFrom = this?.year?.from
    val yearTo = this?.year?.to
    val yearCertain = (yearFrom ?: yearTo).takeIf { yearFrom == null || yearTo == null }

    return DbRequestFilters(
        yearFrom = yearFrom.takeIf { yearCertain == null },
        yearTo = yearTo.takeIf { yearCertain == null },
        yearCertain = yearCertain,
        rating = this?.rating?.from,
        countries = this?.countries?.selectedOptions(),
        types = this?.types?.selectedOptions(),
        networks = this?.networks?.selectedOptions(),
        genres = this?.genres?.selectedOptions(),
        ageRatings = this?.ageRatings?.selectedOptions(),
    )
}