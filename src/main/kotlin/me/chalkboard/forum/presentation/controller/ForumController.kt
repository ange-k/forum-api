package me.chalkboard.forum.presentation.controller

import kotlinx.coroutines.flow.Flow
import me.chalkboard.forum.api.GamesApi
import me.chalkboard.forum.model.Game
import me.chalkboard.forum.usecase.game.GameUsecase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

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
    fun getGame(): Flux<Game> = gameUsecase.findGames()

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