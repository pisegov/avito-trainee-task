package com.myaxa.movies.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.myaxa.domain.movie_details.Review
import com.myaxa.domain.movie_details.ReviewType

@Entity(
    tableName = "review",
    foreignKeys = [
        ForeignKey(
            entity = MovieDBO::class,
            parentColumns = ["movie_id"],
            childColumns = ["movie_id"],
        )
    ]
)
data class ReviewDBO(
    @ColumnInfo("id") @PrimaryKey val id: Long = 0,
    @ColumnInfo("movie_id") val movieId: Long,
    @ColumnInfo("author") val author: String,
    @ColumnInfo("date") val date: String,
    @ColumnInfo("type") val type: ReviewType,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("text") val text: String,
) : MovieDetailsInfoDBO {
    companion object : MovieDetailsInfoDBOCreator<Review, ReviewDBO> {
        override fun fromDomainModel(domainModel: Review): ReviewDBO = with(domainModel) {
            ReviewDBO(
                id = id,
                movieId = movieId,
                author = author,
                date = date,
                title = title,
                type = type,
                text = review,
            )
        }
    }

    override fun toDomainModel(): Review = Review(
        id = id,
        movieId = movieId,
        author = author,
        date = date,
        title = title,
        review = text,
        type = type
    )
}