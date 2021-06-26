package me.chalkboard.forum.infra.post

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface PostRepository: ReactiveCassandraRepository<PostTableModel,PostTableKey> {
    fun findByGameId(gameId:String, writeDay:String): Flux<PostTableModel>
}