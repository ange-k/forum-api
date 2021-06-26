package me.chalkboard.forum.infra.post

import me.chalkboard.forum.model.UserData
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

@Table("posts")
data class PostTableModel(
    @PrimaryKey
    val key: PostTableKey,

    val server: String,
    val playerName: String,
    val purpose: String,
    val vcUse: Boolean,
    val device: String,
    val comment: String,
    val userData: Map<String, String>,
    val deleteKey: String,
) {
    fun userDataConvert(): UserData {
        val ipAddr: String = userData.getOrDefault("ip_addr", "0")
        val userAgent: String = userData.getOrDefault("user_agent", "-")
        return UserData(ipAddr, userAgent)
    }
}