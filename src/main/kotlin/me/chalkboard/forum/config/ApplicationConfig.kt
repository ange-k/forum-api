package me.chalkboard.forum.config

import org.springframework.boot.autoconfigure.cassandra.CassandraProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock

@Configuration
@EnableConfigurationProperties(CassandraProperties::class)
class ApplicationConfig {
    @Bean
    fun clock(): Clock = Clock.systemDefaultZone()
}