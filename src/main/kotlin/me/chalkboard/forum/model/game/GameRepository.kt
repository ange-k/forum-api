package me.chalkboard.forum.model.game

import me.chalkboard.forum.usecase.game.GameTableModel
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository
import org.springframework.stereotype.Repository

@Repository
interface GameRepository: ReactiveCassandraRepository<GameTableModel, String>