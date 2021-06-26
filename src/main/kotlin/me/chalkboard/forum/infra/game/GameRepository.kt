package me.chalkboard.forum.infra.game

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository
import org.springframework.stereotype.Repository

@Repository
interface GameRepository: ReactiveCassandraRepository<GameTableModel, String>