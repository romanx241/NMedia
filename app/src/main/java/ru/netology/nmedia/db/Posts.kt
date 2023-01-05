package ru.netology.nmedia.db

import ru.netology.nmedia.dto.Post

internal fun PostEntity.toModel() = Post(
    id = id,
    author = author,
    published = published,
    content = content,
    likedByMe = likedByMe,
    likes = likes,
    countLike = countLike,
    countShare = countShare,
    countEye = countEye,
    videoUrl = videoUrl

)

internal fun Post.toEntity() = PostEntity(
    id = id,
    author = author,
    published = published,
    content = content,
    likedByMe = likedByMe,
    likes = likes,
    countLike = countLike,
    countShare = countShare,
    countEye = countEye,
    videoUrl = videoUrl

)