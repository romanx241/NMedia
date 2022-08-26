package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun like(postId: Long)
    fun share(postId: Long)
    fun eye(postId: Long)
    fun delete(postId: Long)
    fun save(post: Post)
    fun update(post: Post)
    fun insert(post: Post)

    companion object{
        const val NEW_POST_ID = 0L
    }

}

