package ru.netology.nmedia.dto

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    var id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likedByMe: Boolean = false,
    var likes: Int = 0,
    var countLike: Int,
    var countShare: Int,
    var countEye: Int,
    val videoUrl: String?

    ) {
        val videoAttachment = videoUrl != null
}
