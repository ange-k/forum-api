package me.chalkboard.forum.usecase.post

import me.chalkboard.forum.model.Post

/**
 * Application -> DatastoreのDTO
 */
class PostRequestDto(
    val gameId: String,
    val playerName: String,
    val purpose: Post.Purpose,
    val vcUse: Boolean,
    val device: String,
    val comment: String
) {
}