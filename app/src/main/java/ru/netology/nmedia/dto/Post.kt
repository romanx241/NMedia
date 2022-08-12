package ru.netology.nmedia.dto


data class Post (
    var id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likedByMe: Boolean = false,
    var likes: Int = 0,
    var countLike: Double,
    var countShare: Double,
    var countEye: Double
)
