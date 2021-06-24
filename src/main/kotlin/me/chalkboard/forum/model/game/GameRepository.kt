package me.chalkboard.forum.model.game

import me.chalkboard.forum.usecase.game.GamesResponseDto
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository

interface GameRepository: ReactiveCassandraRepository<GamesResponseDto, Void>{
}