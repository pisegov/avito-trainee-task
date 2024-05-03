package com.myaxa.movies.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.myaxa.domain.movie_details.Actor
import com.myaxa.domain.movie_details.DetailsInfoModel

@Entity(tableName = "actors")
data class ActorDBO (
    @ColumnInfo("actor_id") @PrimaryKey val id: Long,
    @ColumnInfo("name") val name: String? = null,
    @ColumnInfo("photo") val photo: String? = null,
) : MovieDetailsInfoDBO {
    companion object : MovieDetailsInfoDBOCreator<Actor, ActorDBO> {
        override fun fromDomainModel(domainModel: Actor): ActorDBO {
            return ActorDBO(
                id = domainModel.id,
                name = domainModel.name,
                photo = domainModel.photo,
            )
        }
    }
    override fun toDomainModel(): DetailsInfoModel = Actor(
        id = id,
        name = name,
        photo = photo,
    )
}

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