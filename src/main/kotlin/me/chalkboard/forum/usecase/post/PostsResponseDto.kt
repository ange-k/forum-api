package me.chalkboard.forum.usecase.post

import me.chalkboard.forum.model.Post
import java.time.LocalDateTime

/**
 * User <- Application <- DatastoreのDTO
 */
class PostsResponseDto(
    val id: Long,
    val gameId: String,
    val playerName: String,
    val purpose: Post.Purpose,
    val vcUse: Boolean,
    val device: String,
    val comment: String,
    val createdAt: LocalDateTime
)