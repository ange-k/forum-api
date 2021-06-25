package me.chalkboard.forum.presentation.controller

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import me.chalkboard.forum.model.Game
import me.chalkboard.forum.usecase.game.GameUsecase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * GameフォーラムAPIエンドポイント
 */
@RestController
@RequestMapping("\${api.base-path:}")
class ForumController(
    val gameUsecase: GameUsecase
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
    /**
     * そのゲームの投稿を返す
     */
    fun getGamesPosts(game: String): ResponseEntity<Flow<Post>> {
    }

    /**
     * そのゲームに紐づく投稿を行う
     */
    suspend fun postGamesPost(game: String, post: Post?): ResponseEntity<Unit> {
    }
    **/
}