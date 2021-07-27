package me.chalkboard.forum.infra.config

import org.springframework.boot.autoconfigure.cassandra.CassandraProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration
import org.springframework.data.cassandra.config.CqlSessionFactoryBean
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext
import org.springframework.data.cassandra.core.mapping.NamingStrategy
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories

@Configuration
@EnableReactiveCassandraRepositories
class CassandraConfig(
    val cassandraProperties: CassandraProperties,
    val resourceLoader: ResourceLoader,
    val referenceConfig: ReferenceConfig
): AbstractReactiveCassandraConfiguration() {
    override fun getKeyspaceName(): String {
        return cassandraProperties.keyspaceName
    }

    override fun cassandraMapping(): CassandraMappingContext {
        val context:CassandraMappingContext = super.cassandraMapping()
        context.setNamingStrategy(NamingStrategy.SNAKE_CASE)
        return context
    }

   @Bean
    override fun cassandraSession(): CqlSessionFactoryBean {
       val session = super.cassandraSession()
       session.setUsername(cassandraProperties.username)
       session.setPassword(cassandraProperties.password)
       return session
   }

    override fun getDriverConfigurationResource(): Resource {
        return resourceLoader.getResource(referenceConfig.getFilePath())
    }
}