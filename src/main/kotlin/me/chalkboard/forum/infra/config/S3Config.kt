package me.chalkboard.forum.infra.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Bean
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client;

@ConstructorBinding
@ConfigurationProperties(prefix="s3")
class S3Config(
    val bucketName:String
) {
    @Bean
    fun client():S3Client {
        return S3Client.builder()
            .region(Region.AP_NORTHEAST_1)
            .build()
    }
}