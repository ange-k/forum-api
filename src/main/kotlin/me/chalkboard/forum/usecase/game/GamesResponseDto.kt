package me.chalkboard.forum.usecase.game

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.chalkboard.forum.model.Game

/**
 * User <- Application <- DatastoreのDTO
 */
class GamesResponseDto(
    private val games: Flow<Game>
) {
    fun toModels(): Flow<Game> {
        return games
    }

    companion object {
        fun of(games:Flow<Game>):GamesResponseDto {
            return GamesResponseDto(games)
        }
    }
}