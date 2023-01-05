package ru.netology.nmedia.data.impl

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post

class NewRepository (private val context: Context
) : PostRepository {

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private var nextId = 1L
    private var posts = emptyList<Post>()
    override val data = MutableLiveData(posts)

    init {
        val file = context.filesDir.resolve(FILE_NAME)
        if (file.exists()) {
            context.openFileInput(FILE_NAME).bufferedReader().use {
                posts = gson.fromJson(it, type)
                data.value = posts
            }
        } else {
            sync()
        }
    }

    fun getAll(): LiveData<List<Post>> = data


    override fun like(postId: Long) {
        posts = posts.map { it ->
            if (it.id != postId) it
            else it.copy(likedByMe = ! it.likedByMe)
        }
        posts = posts.map { post ->
            if (post.id == postId) {
                if (post.likedByMe) post.copy(countLike = post.countLike - 1)
                else post.copy(countLike = post.countLike + 1)
            } else post
        }
        data.value
        sync()
    }

    override fun share(postId: Long) {
        posts = posts.map { post ->
            if (post.id == postId) {
                post.copy(countShare = post.countShare + 1)
            } else post
        }
        data.value
        sync()
    }

    override fun eye(postId: Long) {
        posts = posts.map { post ->
            if (post.id == postId) {
                post.copy(countEye = post.countEye + 1)
            } else post
        }
        data.value = posts
        sync()
    }

    override fun delete(postId: Long) {
        posts = posts.filterNot {
            it.id == postId
        }
        data.value = posts
        sync()
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    override fun update(post: Post) {
        posts = posts.map {
            if (it.id == post.id) post else it
        }
        data.value = posts
        sync()
    }

    override fun insert(post: Post) {
        posts = listOf(
            post.copy(
                id = ++ nextId
            )
        ) + posts
        data.value = posts
        sync()
    }

    private fun sync() {
        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }

    companion object {
        const val FILE_NAME = "posts.json"

    }
}

