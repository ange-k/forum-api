package me.chalkboard.forum.model.post

import me.chalkboard.forum.usecase.post.PostRequestDto
import me.chalkboard.forum.usecase.post.PostsResponseDto

interface PostRepository {
    fun finds(gameId: String): PostsResponseDto

    fun save(body: PostRequestDto): Void
}