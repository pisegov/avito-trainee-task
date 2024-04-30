package com.myaxa.movies.database.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface MoviesFilteringDao {
    @Query(
        "select movie_id from movies " +
            "where :query = '' or name like '%'|| :query ||'%'"
    )
    fun getMovieIdListByQuery(query: String): List<Long>

    @Query(
        "select movie_id from movies " +
            "where movie_id in (:moviesIds) " +
            "and (:from is null or :to is null or year between :from and :to)"
    )
    fun filterMoviesByYearsInterval(from: Int?, to: Int?, moviesIds: List<Long>): List<Long>

    @Query(
        "select movie_id from movies " +
            "where movie_id in (:moviesIds) " +
            "and (year = :year)"
    )
    fun filterMoviesByCertainYear(year: Int, moviesIds: List<Long>): List<Long>

    @Query(
        "select movie_id from movies " +
            "where movie_id in (:moviesIds) " +
            "and (:rating is null or rating >= :rating)"
    )
    fun filterMoviesByRating(rating: Double?, moviesIds: List<Long>): List<Long>

    @Query(
        "select movie_id from movies " +
            "left join content_type on movies.type_id = content_type.type_id " +
            "where movie_id in (:moviesIds) " +
            "and (:types is null or content_type.title in (:types))"
    )
    fun filterMoviesByContentType(types: List<String>?, moviesIds: List<Long>): List<Long>

    @Query(
        "select distinct movies.movie_id from movies " +
            "left join movie_country on movies.movie_id = movie_country.movie_id " +
            "left join countries on movie_country.country_id = countries.country_id " +
            "where movies.movie_id in (:moviesIds) " +
            "and (:countries is null or countries.title in (:countries))"
    )
    fun filterMoviesByCountry(countries: List<String>?, moviesIds: List<Long>): List<Long>

    @Query(
        "select movie_id from movies " +
            "left join network on movies.network_id = network.network_id " +
            "where movie_id in (:moviesIds) " +
            "and (:networks is null or network.title in (:networks))"
    )
    fun filterMoviesByNetwork(networks: List<String>?, moviesIds: List<Long>): List<Long>

    @Query(
        "select distinct movies.movie_id from movies " +
            "left join movie_genre on movies.movie_id = movie_genre.movie_id " +
            "left join genres on movie_genre.genre_id = genres.genre_id " +
            "where movies.movie_id in (:moviesIds) " +
            "and (:genres is null or genres.title in (:genres))"
    )
    fun filterMoviesByGenre(genres: List<String>?, moviesIds: List<Long>): List<Long>

    @Query(
        "select movie_id from movies " +
            "left join age_rating on movies.age_rating_id = age_rating.age_rating_id " +
            "where movie_id in (:moviesIds) " +
            "and (:ageRatings is null or age_rating.value in (:ageRatings))"
    )
    fun filterMoviesByAgeRating(ageRatings: List<String>?, moviesIds: List<Long>): List<Long>
}