package me.chalkboard.forum.usecase.post

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactive.asFlow
import me.chalkboard.forum.infra.game.GameDatasource
import me.chalkboard.forum.infra.post.PostDatasource
import me.chalkboard.forum.infra.post.PostTableModel
import me.chalkboard.forum.model.DeleteRequest
import me.chalkboard.forum.model.Post
import me.chalkboard.forum.model.domain.image.Base64Image
import me.chalkboard.forum.model.domain.image.ImageRepository
import me.chalkboard.forum.usecase.exception.InternalException
import me.chalkboard.forum.usecase.exception.NotFoundException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.lang.Exception
import java.time.LocalDate
import java.util.*

@Service
class PostUsecase(
    private val gameRepository: GameDatasource,
    private val postRepository: PostDatasource,
    private val imageRepository: ImageRepository
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

    fun save(post: Post): Mono<Void> {
        var objectKey: String? = null //よくないが…
        try {
            if(!post.imageData.isNullOrBlank()) {
                objectKey = "${LocalDate.now()}/${UUID.randomUUID()}.png";
                imageRepository.upload(
                    Base64Image.of(post.imageData),
                    objectKey
                )
            }
        } catch (e: Exception) {
            return Mono.error(InternalException("S3エラー"))
        }

        return postRepository.save(post, objectKey)
            .onErrorResume(Exception::class.java) { ex ->
                log.error("想定外の例外:" + ex.message)
                Mono.error(ex)
            }
    }

    fun delete(uuid: String, deleteRequest: DeleteRequest): Flux<Void> {
        val result: Flux<PostTableModel> = postRepository.findByGameIDAndWriteDay(deleteRequest.gameId, LocalDate.parse(deleteRequest.writeDay))
            .filter { post -> post.key.uuid.toString() == uuid }
            .switchIfEmpty(Mono.error(NotFoundException("削除対象が見つかりませんでした")))

        return result.flatMap {
            if(it.deleteKey == deleteRequest.deleteKey) {
                return@flatMap postRepository.delete(it).then()
            }
            return@flatMap Mono.error(NotFoundException("削除キーが異なります"))
        }
    }
}