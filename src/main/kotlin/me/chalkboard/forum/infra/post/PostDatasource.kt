package me.chalkboard.forum.infra.post

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import me.chalkboard.forum.model.Post
import me.chalkboard.forum.model.type.datetime.Date
import me.chalkboard.forum.usecase.post.PostRequestDto
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.Clock
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
            }.map { model ->
                Post(model.key.gameId, model.playerName, Post.Purpose.valueOf(model.purpose), model.vcUse, model.device, model.comment,
                model.key.uuid, model.key.writeDay, model.server, model.key.createdAt, model.userDataConvert(), model.deleteKey)
            }.asFlow()
    }

    fun save(body: PostRequestDto): Void {
        TODO("Not yet implemented")
    }
}