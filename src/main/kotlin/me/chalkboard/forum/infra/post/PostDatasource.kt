package me.chalkboard.forum.infra.post

import me.chalkboard.forum.model.post.PostRepository
import me.chalkboard.forum.usecase.post.PostRequestDto
import me.chalkboard.forum.usecase.post.PostsResponseDto
import org.springframework.stereotype.Repository

@Repository
class PostDatasource: PostRepository {
    override fun finds(gameId: String): PostsResponseDto {
        TODO("Not yet implemented")
    }

    override fun save(body: PostRequestDto): Void {
        TODO("Not yet implemented")
    }
}