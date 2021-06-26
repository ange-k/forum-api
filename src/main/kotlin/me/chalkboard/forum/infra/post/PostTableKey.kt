package me.chalkboard.forum.infra.post

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import java.time.LocalDateTime
import java.io.Serializable

@PrimaryKeyClass
class PostTableKey(
    @PrimaryKeyColumn(name="game_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    val gameId: String,
    @PrimaryKeyColumn(name="write_day", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    val writeDay: String,
    @PrimaryKeyColumn(name="created_at", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    val createdAt: LocalDateTime?,
    @PrimaryKeyColumn(name="uuid", ordinal = 3, type = PrimaryKeyType.CLUSTERED)
    val uuid: String?,
): Serializable