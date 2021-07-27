package me.chalkboard.forum.infra.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("prod")
@Configuration
class ReferenceConfigProd:ReferenceConfig {
    override fun getFilePath(): String {
        return "classpath:reference.prod.conf"
    }
}