package me.chalkboard.forum.infra.post

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.LocalDate

@Repository
interface PostRepository: ReactiveCassandraRepository<PostTableModel,PostTableKey> {
    /**
     * (関数名を名前でたどる挙動をするので名前を変えないこと)
     */
    fun findByKeyGameIdAndKeyWriteDay(gameId:String, writeDay:LocalDate): Flux<PostTableModel>
}