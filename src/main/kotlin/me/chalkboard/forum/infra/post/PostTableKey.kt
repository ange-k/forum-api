package me.chalkboard.forum.infra.post

import org.springframework.data.cassandra.core.cql.Ordering
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@PrimaryKeyClass
data class PostTableKey(
    @PrimaryKeyColumn(name="game_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    var gameId: String,
    @PrimaryKeyColumn(name="write_day", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    val writeDay: LocalDate,
    @PrimaryKeyColumn(name="created_at", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    val createdAt: LocalDateTime?,
    @PrimaryKeyColumn(name="uuid", ordinal = 3, type = PrimaryKeyType.CLUSTERED)
    val uuid: UUID?,
)