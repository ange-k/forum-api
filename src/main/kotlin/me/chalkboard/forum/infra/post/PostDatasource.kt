package me.chalkboard.forum.infra.post

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.reactive.asFlow
import me.chalkboard.forum.model.Post
import me.chalkboard.forum.model.type.datetime.Date
import me.chalkboard.forum.usecase.post.PostRequestDto
import me.chalkboard.forum.usecase.post.PostsResponseDto
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.Clock
import java.util.stream.Collectors
import java.util.stream.IntStream
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
        val requests:List<PostTableKey> = LongStream.range(0, 6).mapToObj { daysAgo ->
            PostTableKey(gameId, Date.daysAgo(daysAgo, clock).toString(), null, null)
        }.collect(Collectors.toList())

        return Flux.fromIterable(requests)
            .flatMap { request ->
                repository.findByGameId(request.gameId, request.writeDay)
            }.map { model ->
                Post(model.key.gameId, model.playerName, Post.Purpose.valueOf(model.purpose), model.vcUse, model.device, model.comment,
                model.key.uuid, model.server, model.key.createdAt, model.userData, model.deleteKey)
            }.asFlow()
    }

    fun save(body: PostRequestDto): Void {
        TODO("Not yet implemented")
    }
}