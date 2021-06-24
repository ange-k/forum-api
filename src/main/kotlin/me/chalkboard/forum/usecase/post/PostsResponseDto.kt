package me.chalkboard.forum.usecase.post

import kotlinx.coroutines.flow.Flow
import me.chalkboard.forum.model.Post
import java.time.LocalDateTime

/**
 * User <- Application <- DatastoreのDTO
 */
class PostsResponseDto(
    val posts: Flow<PostDto>
) {
    data class PostDto(
        val id: Long,
        val gameId: String,
        val playerName: String,
        val purpose: Post.Purpose,
        val vcUse: Boolean,
        val device: String,
        val comment: String,
        val createdAt: LocalDateTime
    )
}