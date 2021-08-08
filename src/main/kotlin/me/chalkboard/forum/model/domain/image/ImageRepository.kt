package me.chalkboard.forum.model.domain.image

interface ImageRepository {
    fun upload(data:Base64Image, objectPath:String)
}