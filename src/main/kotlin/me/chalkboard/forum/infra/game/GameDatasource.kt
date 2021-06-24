package me.chalkboard.forum.infra.game

import kotlinx.coroutines.flow.flow
import me.chalkboard.forum.model.game.GameRepository
import me.chalkboard.forum.usecase.game.GamesResponseDto
import org.springframework.stereotype.Repository

/**
 * Reactive-Cassandraの実装を委譲して利用するDatasource.
 * これそのもののIFを定義していないのでホンモノがDIされる.
 */
@Repository
class GameDatasource(
    val repository: GameRepository
) {
    fun finds(): GamesResponseDto {
        return GamesResponseDto.of(
            flow {
                repository.findAll()
            }
        )
    }
}