package me.chalkboard.forum.model.domain.image

import org.apache.commons.codec.binary.Base64
import java.io.ByteArrayInputStream
import java.io.InputStream

class Base64Image(
    val dataStream: InputStream,
    val byteLength: Int
) {
    companion object {
        fun of(base64Data: String):Base64Image {
            val removedHeaderData = base64Data.removePrefix("data:image/png;base64,")
            val decodeData = Base64.decodeBase64(removedHeaderData)
            return Base64Image(ByteArrayInputStream(decodeData), decodeData.size)
        }
    }
}