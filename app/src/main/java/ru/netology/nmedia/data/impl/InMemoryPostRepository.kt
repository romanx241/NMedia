package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post

    class InMemoryPostRepository() : PostRepository {

        private var nextId = GENERATED_POSTS_AMOUNT.toLong()

        private var posts = List(GENERATED_POSTS_AMOUNT) { index ->
            Post(
                id = index + 1L,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Контент поста №${index + 1}",
                published = "21 мая в 18:36",
                likedByMe = false,
                countLike = 990,
                countShare = 990,
                countEye = 990,
                videoUrl = null
            )
        }
        override val data = MutableLiveData(posts)


        override fun like(postId: Long) {
            data.value = posts.map { it ->
                if (it.id != postId) it
                else it.copy(likedByMe = ! it.likedByMe)
            }
            posts = posts.map { post ->
                if (post.id == postId) {
                    if (post.likedByMe) post.copy(countLike = post.countLike - 1)
                    else post.copy(countLike = post.countLike + 1)
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
        override fun delete(postId: Long) {
            data.value = posts.filterNot {
                it.id == postId
            }
            data.value = posts
        }
        override fun save(post: Post) {
            if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
        }
         override fun update(post: Post) {
            data.value = posts.map {
                if (it.id == post.id) post else it
            }
        }
         override fun insert(post: Post) {
            data.value = listOf(
                post.copy(
                    id = ++ nextId
                )
            ) + posts
        }

        companion object {
            const val GENERATED_POSTS_AMOUNT = 1000
        }
    }


