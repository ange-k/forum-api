package me.chalkboard.forum.infra.config

import com.datastax.oss.driver.api.core.CqlSessionBuilder
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration
import org.springframework.data.cassandra.config.CqlSessionFactoryBean
import org.springframework.data.cassandra.config.SessionBuilderConfigurer
import org.springframework.data.cassandra.core.ReactiveCassandraTemplate
import org.springframework.data.cassandra.core.cql.ReactiveCqlOperations
import org.springframework.data.cassandra.core.cql.ReactiveCqlTemplate
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext
import org.springframework.data.cassandra.core.mapping.NamingStrategy
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories
import javax.net.ssl.SSLContext

@Configuration
@EnableReactiveCassandraRepositories
class CassandraConfig(
    val cassandraProperties: CassandraProperties,
    @Value("\${spring.profiles.active}") val env:String
): AbstractReactiveCassandraConfiguration() {

    override fun getKeyspaceName(): String {
        return cassandraProperties.keyspaceName
    }

    override fun getLocalDataCenter(): String? {
        return cassandraProperties.localDatacenter
    }

    override fun getContactPoints(): String {
        return cassandraProperties.contactPoints[0]
    }

    override fun getPort(): Int {
        return cassandraProperties.port
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

    /**
     * https://github.com/spring-projects/spring-boot/issues/25602
     */
    override fun getSessionBuilderConfigurer(): SessionBuilderConfigurer? {
        if(env != "prod") {
            return super.getSessionBuilderConfigurer()
        }
        return SessionBuilderConfigurer { cqlSessionBuilder: CqlSessionBuilder ->
            val sslContext: SSLContext = SSLContext.getDefault()
            cqlSessionBuilder.withSslContext(sslContext)
        }
    }

    /**
     * reactiveCqlTemplateOption=reactiveCqlTemplateだが、
     * Bean登録したところでこのCassandraTemplateの作成時に入れ込まないと意味がない模様
     */
    @Bean
    override fun reactiveCassandraTemplate(): ReactiveCassandraTemplate {
        val cassandraTemplate: ReactiveCassandraTemplate = super.reactiveCassandraTemplate()
        val cqlTemplate:ReactiveCqlTemplate = super.reactiveCqlTemplate()

        cqlTemplate.consistencyLevel = cassandraProperties.request.consistency

        return ReactiveCassandraTemplate(
            cqlTemplate, cassandraTemplate.converter
        )
    }

    @Bean
    fun reactiveCqlTemplateOption(): ReactiveCqlOperations {
        val template:ReactiveCqlTemplate = super.reactiveCqlTemplate()
        template.consistencyLevel = cassandraProperties.request.consistency
        return template
    }
}