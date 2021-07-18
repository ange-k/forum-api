package me.chalkboard.forum.infra.post

import org.springframework.data.cassandra.repository.Query
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate

@Repository
interface PostRepository: ReactiveCassandraRepository<PostTableModel,PostTableKey> {
    /**
     * (関数名を名前でたどる挙動をするので名前を変えないこと)
     */
    fun findByKeyGameIdAndKeyWriteDay(gameId:String, writeDay:LocalDate): Flux<PostTableModel>

    @Query("INSERT INTO forum.posts(uuid, write_day, game_id, server, title, player_name, purpose, vc_use, device, comment, created_at, user_data, tags, self_tags, play_time, delete_key) VALUES (" +
            "uuid(), :#{#model.key.writeDay}, :#{#model.key.gameId}, :#{#model.server}, :#{#model.title}, :#{#model.playerName}, :#{#model.purpose}, :#{#model.vcUse}," +
            ":#{#model.device}, :#{#model.comment}, toTimeStamp(now()), :#{#model.userData}, :#{#model.tags}, :#{#model.selfTags}, :#{#model.playTime}, :#{#model.deleteKey})")
    fun save(@Param("model")model: PostTableModel): Mono<Void>
}