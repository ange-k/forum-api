package me.chalkboard.forum.infra.post

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import me.chalkboard.forum.model.Post
import me.chalkboard.forum.model.type.datetime.Date
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Clock
import java.util.*
import java.util.stream.Collectors
import java.util.stream.LongStream

@Repository
class PostDatasource(
    private val repository: PostRepository,
    private val clock: Clock
) {
    /**
     * 1週間分のデータの取得を行う
     */
    suspend fun finds(gameId: String): Flow<Post> {
        val requests:List<Date> = LongStream.range(0, 6).mapToObj { daysAgo ->
            Date.daysAgo(daysAgo, clock)
        }.collect(Collectors.toList())

        return Flux.fromIterable(requests)
            .flatMap { request ->
                repository.findByKeyGameIdAndKeyWriteDay(gameId, request.value())
            }.map(this::convertModel).asFlow()
    }

    fun save(post: Post): Mono<Void> {
        val userDataDto: UserDataDto = UserDataDto.of(post.userData)
        val model = PostTableModel(
            PostTableKey(post.gameId, Date.now(clock).value(), null, null),
            post.server.orEmpty(), post.playerName, post.purpose.value, post.vcUse, post.device, post.comment,
            userDataDto.convertMap()
            , post.deleteKey.orEmpty()
        )
        return repository.save(model)
    }

    fun convertModel(model: PostTableModel): Post =
        Post(model.key.gameId, model.playerName, Post.Purpose.valueOf(model.purpose), model.vcUse, model.device, model.comment,
            model.key.uuid, model.key.writeDay, model.server, model.key.createdAt, UserDataDto.of(model.userData).convertModel(), model.deleteKey)
}