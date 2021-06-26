package me.chalkboard.forum.usecase.post

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.reactive.asFlow
import me.chalkboard.forum.infra.game.GameDatasource
import me.chalkboard.forum.infra.post.PostDatasource
import me.chalkboard.forum.model.Post
import me.chalkboard.forum.usecase.exception.NotFoundException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.lang.Exception

@Service
class PostUsecase(
    private val gameRepository: GameDatasource,
    private val postRepository: PostDatasource
) {
    companion object {
        private val log = LoggerFactory.getLogger(PostUsecase::class.java)
    }

    suspend fun finds(gameId: String): Flow<Post> {
        val gameIds:Flow<String> = gameRepository.finds().map { model ->
            model.idName
        }.asFlow()

        if(gameIds.filter { m -> m == gameId }.count() == 0) {
            throw NotFoundException("存在しないGameIDを指定.")
        }
        log.debug("gameidが存在したので処理を続行")

        return postRepository.finds(gameId)
    }

    fun save(post: Post): Mono<Void> =
        postRepository.save(post)
            .onErrorResume(Exception::class.java) { ex ->
                log.error("想定外の例外:" + ex.message)
                Mono.error(ex)
            }
}