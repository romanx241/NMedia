package ru.netology.nmedia.data.impl

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post

class FilePostRepository(private val application: Application
) : PostRepository {

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private var posts = emptyList<Post>()
    private var data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

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

