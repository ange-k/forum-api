package me.chalkboard.forum.infra.post

import me.chalkboard.forum.model.UserData

class UserDataDto private constructor(
    val ipAddr: String,
    val userAgent: String
) {
    companion object {
        fun of(userData: Map<String, String>): UserDataDto {
            val ip = userData.getOrDefault("ip_addr", "")
            val agent = userData.getOrDefault("user_agent", "")
            return UserDataDto(ip, agent)
        }

        fun of(userData: UserData?):UserDataDto = userData?.let {
            val ipAddr: String = it.ipAddr ?: ""
            val userAgent: String = it.userAgent ?: ""
            UserDataDto(ipAddr, userAgent)
        } ?: run {
            UserDataDto("", "")
        }
    }

    fun convertMap() = mapOf(
        "ip_addr" to this.ipAddr,
        "user_agent" to this.userAgent
    )

    fun convertModel():UserData = UserData(ipAddr, userAgent)
}