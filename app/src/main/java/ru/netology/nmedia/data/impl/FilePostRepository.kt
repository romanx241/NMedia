package ru.netology.nmedia.data.impl

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post
import kotlin.properties.Delegates

class FilePostRepository(private val application: Application
) : PostRepository {

    private val gson = Gson()
    private val prefs = application.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type

    private var nextId: Long by Delegates.observable(prefs.getLong(NEXT_ID_PREFS_KEY, 0L)) {
            _,_, newValue ->
        prefs.edit{
            putLong(NEXT_ID_PREFS_KEY, newValue)
        }
    }

    private var posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }
        set(value) {
            application.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
                .bufferedWriter().use {
                    it.write(gson.toJson(value))
                }
            data.value = value
        }
    override val data : MutableLiveData<List<Post>>


    init {
        val postsFile = application.filesDir.resolve(FILE_NAME)
        val posts: List<Post> = if(postsFile.exists()){
            val inputStream = application.openFileInput(FILE_NAME)
            val reader = inputStream.bufferedReader()
            reader.use { gson.fromJson(it, type) }
        } else emptyList()
        data = MutableLiveData(posts)
    }


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
    }

    override fun share(postId: Long) {
        posts = posts.map { post ->
            if (post.id == postId) {
                post.copy(countShare = post.countShare + 1)
            } else post
        }
    }

    override fun eye(postId: Long) {
        posts = posts.map { post ->
            if (post.id == postId) {
                post.copy(countEye = post.countEye + 1)
            } else post
        }
    }

    override fun delete(postId: Long) {
        posts = posts.filterNot {
            it.id == postId
        }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    override fun update(post: Post) {
        posts = posts.map {
            if (it.id == post.id) post else it
        }
    }

    override fun insert(post: Post) {
        posts = listOf(
            post.copy(
            )
        ) + posts
    }



    companion object {
        const val POSTS_PREFS_KEY = "posts"
        const val FILE_NAME = "posts.json"
        const val NEXT_ID_PREFS_KEY = "nextId"

    }
}

