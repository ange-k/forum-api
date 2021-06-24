package me.chalkboard.forum.infra.config

import org.springframework.boot.autoconfigure.cassandra.CassandraProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration
import org.springframework.data.cassandra.config.CqlSessionFactoryBean
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories

@Configuration
@EnableReactiveCassandraRepositories
class CassandraConfig(
    val cassandraProperties: CassandraProperties
): AbstractReactiveCassandraConfiguration() {
    override fun getKeyspaceName(): String {
        return cassandraProperties.keyspaceName
    }

    @Bean
    override fun cassandraSession(): CqlSessionFactoryBean {
        val session = super.cassandraSession()
        session.setContactPoints(cassandraProperties.contactPoints[0])
        session.setPort(cassandraProperties.port)
        session.setLocalDatacenter(cassandraProperties.localDatacenter)
        session.setKeyspaceName(cassandraProperties.keyspaceName)
        session.setUsername(cassandraProperties.username)
        session.setPassword(cassandraProperties.password)
        return session
    }
}