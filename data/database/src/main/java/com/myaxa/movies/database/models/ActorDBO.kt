package com.myaxa.movies.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "actors")
data class ActorDBO (
    @ColumnInfo("actor_id") @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("name") val name: String? = null,
    @ColumnInfo("photo") val photo: String? = null,
)

@Entity(
    tableName = "movies_actors",
    primaryKeys = ["movie_id", "actor_id"],
    foreignKeys = [
        ForeignKey(
            entity = MovieDBO::class,
            parentColumns = ["movie_id"],
            childColumns = ["movie_id"],
        ),
        ForeignKey(
            entity = ActorDBO::class,
            parentColumns = ["actor_id"],
            childColumns = ["actor_id"],
        ),
    ]
)
data class MovieActorCrossRef(
    @ColumnInfo("movie_id") val movieId: Long,
    @ColumnInfo("actor_id") val actorId: Long,
)

@Entity(
    tableName = "actor_of_movie",
    foreignKeys = [ForeignKey(
        entity = MovieDBO::class,
        parentColumns = ["movie_id"],
        childColumns = ["movie_id"],
    )],
)
data class MovieActorsDBO(
    @PrimaryKey
    @ColumnInfo("movie_id") val id: Long,

    @Relation(
        parentColumn = "movie_id",
        entity = ActorDBO::class,
        entityColumn = "actor_id",
        associateBy = Junction(MovieActorCrossRef::class)
    )
    @ColumnInfo("actors") val actors: List<ActorDBO>,
)