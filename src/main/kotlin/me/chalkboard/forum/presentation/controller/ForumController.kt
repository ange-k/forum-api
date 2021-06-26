package me.chalkboard.forum.presentation.controller

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import me.chalkboard.forum.model.Game
import me.chalkboard.forum.model.Post
import me.chalkboard.forum.usecase.game.GameUsecase
import me.chalkboard.forum.usecase.post.PostUsecase
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import javax.validation.Valid

/**
 * GameフォーラムAPIエンドポイント
 */
@RestController
@RequestMapping("\${api.base-path:}")
class ForumController(
    val gameUsecase: GameUsecase,
    val postUsecase: PostUsecase
){
    companion object {
        private val log = LoggerFactory.getLogger(ForumController::class.java)
    }

    /**
     * 全ゲームの取得
     */
    @GetMapping(
        value = ["/games"],
        produces = ["application/json", "application/xml"]
    )
    suspend fun getGame(): Flow<Game> = gameUsecase.findGames().asFlow()

    /**
     * そのゲームの投稿を返す
     */
    @GetMapping(
        value = ["/games/{gameId}/posts"],
        produces = ["application/json"]
    )
    suspend fun getGamesPosts(@PathVariable gameId: String): Flow<Post> = postUsecase.finds(gameId)

    /**
     * そのゲームに紐づく投稿を行う
     */
    @PostMapping(
        value = ["/games/{game}/posts"]
    )
    fun postGamesPost(
        @PathVariable("game") game: String,
        @Valid @RequestBody(required = true) post: Post): Mono<Void> = postUsecase.save(post).then()
}