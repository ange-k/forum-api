package me.chalkboard.forum.usecase.game

import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

@Table("games")
class GameTableModel(
    @PrimaryKey
    val idName: String,
    val viewName: String
)