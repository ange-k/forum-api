package me.chalkboard.forum.presentation.controller

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.reactive.asFlow
import me.chalkboard.forum.model.Error
import me.chalkboard.forum.model.Game
import me.chalkboard.forum.model.Post
import me.chalkboard.forum.usecase.game.GameUsecase
import me.chalkboard.forum.usecase.post.PostUsecase
import me.chalkboard.forum.usecase.post.PostsResponseDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.lang.Exception

/**
 * GameフォーラムAPIエンドポイント
 */
@RestController
@RequestMapping("\${api.base-path:}")
class ForumController(
    val gameUsecase: GameUsecase,
    val postUsecase: PostUsecase
){
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
    /**
     * そのゲームに紐づく投稿を行う
     */
    suspend fun postGamesPost(game: String, post: Post?): ResponseEntity<Unit> {
    }
    **/
}