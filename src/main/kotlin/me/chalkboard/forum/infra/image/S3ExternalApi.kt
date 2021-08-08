package me.chalkboard.forum.infra.image

import me.chalkboard.forum.infra.config.S3Config
import me.chalkboard.forum.model.domain.image.Base64Image
import me.chalkboard.forum.model.domain.image.ImageRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.S3Exception
import java.lang.Exception

@Repository
class S3ExternalApi(
    val config: S3Config,
    val client: S3Client
): ImageRepository {
    companion object {
        private val log = LoggerFactory.getLogger(S3ExternalApi::class.java)
    }

    override fun upload(data: Base64Image, objectPath: String) {
        val metaData = HashMap<String, String>()

        val request = PutObjectRequest.builder()
            .bucket(config.bucketName)
            .key(objectPath)
            .metadata(metaData)
            .contentLength(data.byteLength.toLong())
            .contentType("image/png")
            .cacheControl("public, max-age=31536000")
            .build()

        try {
            client.putObject(
                request,
                RequestBody.fromBytes(data.dataStream.readBytes())
            )
        } catch (s3e: S3Exception) {
            log.error("s3 exception.", s3e)
            throw s3e
        } catch (e: Exception) {
            log.error("s3 想定外例外", e)
            throw e
        }
    }
}