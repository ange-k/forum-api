package me.chalkboard.forum.usecase.game

import me.chalkboard.forum.infra.game.GameDatasource
import org.springframework.stereotype.Service

/**
 * Gameリソースアクセスのためのユースケース
 */
@Service
class GameUsecase(
    val repository: GameDatasource,
) {
    fun findGames(): GamesResponseDto {
        return repository.finds()
    }
}