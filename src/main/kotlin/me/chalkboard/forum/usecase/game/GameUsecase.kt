package me.chalkboard.forum.usecase.game

import kotlinx.coroutines.flow.Flow
import me.chalkboard.forum.infra.game.GameDatasource
import me.chalkboard.forum.model.Game
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

/**
 * Gameリソースアクセスのためのユースケース
 */
@Service
class GameUsecase(
    val repository: GameDatasource,
) {
    fun findGames(): Flux<Game> {
        return repository.finds()
    }
}