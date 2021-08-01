package me.chalkboard.forum

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class GameForumApplication

fun main(args: Array<String>) {
	runApplication<GameForumApplication>(*args)
}
