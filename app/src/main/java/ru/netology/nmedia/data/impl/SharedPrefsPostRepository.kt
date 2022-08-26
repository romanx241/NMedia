package ru.netology.nmedia.data.impl

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post

 class SharedPrefsPostRepository(
   private val application: Application
) : PostRepository {

    private val prefs = application.getSharedPreferences(
        "repo", Context.MODE_PRIVATE
    )

    private var nextId = GENERATED_POSTS_AMOUNT.toLong()

    private var posts
    get() = checkNotNull(data.value){
        "Data value"
    }
    set(value) {
        prefs.edit {
            val serializedPost = Json.encodeToString(value)
            putString(POSTS_PREFS_KEY, serializedPost)

        }
        data.value = value
    }

    override fun getAll(): LiveData<List<Post>> = data

     var data = MutableLiveData(List(GENERATED_POSTS_AMOUNT) { index ->
        Post(
            id = index + 1L,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Контент поста №${index + 1}",
            published = "21 мая в 18:36",
            likedByMe = false,
            countLike = 990.0,
            countShare = 990.0,
            countEye = 990.0,
            videoUrl = null
        )
     }
     )

    init {

        val serializedPosts = prefs.getString(POSTS_PREFS_KEY, null)
        val posts: List<Post> = if(serializedPosts == null){
            Json.decodeFromString(serializedPosts.toString())
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
                id = ++ nextId
            )
        ) + posts
    }

    companion object {
        const val GENERATED_POSTS_AMOUNT = 1000
        const val POSTS_PREFS_KEY = "posts"
    }
}