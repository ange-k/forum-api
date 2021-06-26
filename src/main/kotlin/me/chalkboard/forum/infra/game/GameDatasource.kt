package me.chalkboard.forum.infra.game

import me.chalkboard.forum.model.Game
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

/**
 * Reactive-Cassandraの実装を委譲して利用するDatasource.
 * これそのもののIFを定義していないのでホンモノがDIされる.
 */
@Repository
class GameDatasource(
    private val repository: GameRepository
) {
    suspend fun finds(): Flux<Game> {
        return  repository.findAll().map { m -> Game(m.idName, m.viewName) }
    }
}