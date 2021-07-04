package me.chalkboard.forum.infra.post

import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

@Table("posts")
data class PostTableModel(
    @PrimaryKey
    val key: PostTableKey,

    val server: String,
    val title: String,
    val playerName: String,
    val purpose: String,
    val vcUse: String,
    val device: String,
    val comment: String,
    val userData: Map<String, String>,
    val tags: List<String>?,
    val deleteKey: String?,
)