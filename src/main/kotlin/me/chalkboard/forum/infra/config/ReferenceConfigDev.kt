package me.chalkboard.forum.infra.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("!prod")
@Configuration
class ReferenceConfigDev:ReferenceConfig {
    override fun getFilePath(): String {
        return "classpath:reference.conf"
    }
}