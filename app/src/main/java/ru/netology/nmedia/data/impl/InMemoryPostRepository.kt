package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post

    class InMemoryPostRepository() : PostRepository {

        private var posts
            get() = checkNotNull(data.value)
            set(value) {
                data.value = value
            }
        override val data: MutableLiveData<List<Post>>

        init {
            val initialPosts = List(1000) { index ->
                Post(
                    id = index + 1L,
                    author = "Нетология. Университет интернет-профессий будущего",
                    content = "Контент поста №${index + 1}",
                    published = "21 мая в 18:36",
                    likedByMe = false,
                    countLike = 990.0,
                    countShare = 990.0,
                    countEye = 990.0
                )
            }
            data = MutableLiveData(initialPosts)
        }
        override fun like(postId: Long) {
            posts = posts.map { post ->
                if (post.id != postId) post
                else post.copy(likedByMe = !post.likedByMe)
            }
            posts = posts.map { post ->
                if(post.id == postId){
                    if(post.likedByMe) post.copy(countLike = post.countLike + 1)
                    else post.copy(countLike = post.countLike - 1)
                } else post
            }
            data.value = posts
        }
        override fun share(postId: Long) {
            posts = posts.map { post ->
                if (post.id == postId) {
                    post.copy(countShare = post.countShare + 1)
                } else post
            }
            data.value = posts
        }
        override fun eye(postId: Long) {
            posts = posts.map { post ->
                if (post.id == postId) {
                    post.copy(countEye = post.countEye + 1)
                } else post
            }
            data.value = posts
        }
    }




