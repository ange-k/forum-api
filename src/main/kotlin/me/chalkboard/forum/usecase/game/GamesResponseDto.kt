package me.chalkboard.forum.usecase.game

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.chalkboard.forum.model.Game

/**
 * User <- Application <- DatastoreのDTO
 */
class GamesResponseDto(
    private val games: Flow<GameDto>
) {
    fun toModels(): Flow<Game> {
        return games.map { game -> Game(game.id, game.name) }
    }

    data class GameDto(
        val id: String, // uriパラメータになる
        val name: String
    )

    companion object {
        fun of(games:Flow<GameDto>):GamesResponseDto {
            return GamesResponseDto(games)
        }
    }
}