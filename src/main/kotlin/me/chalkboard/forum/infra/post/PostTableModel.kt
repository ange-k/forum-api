package me.chalkboard.forum.infra.post

import me.chalkboard.forum.model.UserData
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.io.Serializable

@Table("posts")
class PostTableModel(
    @PrimaryKey
    val key: PostTableKey,
    val server: String,
    val playerName: String,
    val purpose: String,
    val vcUse: Boolean,
    val device: String,
    val comment: String,
    val userData: UserData,
    val deleteKey: String,
): Serializable