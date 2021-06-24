package me.chalkboard.forum.presentation.controller

import kotlinx.coroutines.flow.Flow
import me.chalkboard.forum.api.GamesApi
import me.chalkboard.forum.model.Game
import me.chalkboard.forum.model.Post
import me.chalkboard.forum.usecase.game.GameUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

/**
 * GameフォーラムAPIエンドポイント
 */
@RestController
class ForumController(
    val gameUsecase: GameUsecase
): GamesApi {
    /**
     * 全ゲームの取得
     */
    override fun getGames(): ResponseEntity<Flow<Game>> {
        val games:Flow<Game> = gameUsecase.findGames().toModels()
        return ResponseEntity.ok().body(games)
    }

    /**
     * そのゲームの投稿を返す
     */
    override fun getGamesPosts(game: String): ResponseEntity<Flow<Post>> {
        return super.getGamesPosts(game)
    }

    /**
     * そのゲームに紐づく投稿を行う
     */
    override suspend fun postGamesPost(game: String, post: Post?): ResponseEntity<Unit> {
        return super.postGamesPost(game, post)
    }
}