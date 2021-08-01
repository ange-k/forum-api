package me.chalkboard.forum.presentation.filter

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix="auth")
class ApiKeyConfig(val apikey: String) {
}