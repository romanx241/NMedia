package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post



    class InMemoryPostRepository() : PostRepository {

        private var post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет! Это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, аналитике и управлению. " +
                    "Мы растем и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остается с нами: мы верим, что в каждом уже есть сила, " +
                    "которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия помочь встать на путь роста и начать цепочку перемен",
            published = "21 мая в 18:36",
            likedByMe = false,
            countLike = 0.0,
            countShare = 0.0,
            countEye = 0.0
        )

        private val data = MutableLiveData(post)
        override fun get(): LiveData<Post> = data


        override fun like() {
            post = post.copy(likedByMe = !post.likedByMe)
            data.value = post
            post.countLike++

        }
        override fun share() {
            post.countShare++
             }

        override fun eye() {
            post.countEye++
            }
    }

