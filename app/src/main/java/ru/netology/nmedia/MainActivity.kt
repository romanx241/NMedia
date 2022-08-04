package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.FunLife
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewModel.PostViewModel


class MainActivity : AppCompatActivity() {

    lateinit var post1: Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val funLife: FunLife = FunLife

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                like.setImageResource(getLikeIconResId(post.likedByMe))
            }
            binding.render(post)
        }
        Post(
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
        binding.like.setOnClickListener {
            viewModel.likeCount()
            funLife.likeCount(numberLike, post1)
        }
        binding.share.setOnClickListener {
            viewModel.shareCount()
            funLife.shareCount(numberShare, post1)
        }
        binding.eye.setOnClickListener {
            viewModel.eyeCount()
            funLife.eyeCount(numberEye, post1)
        }
    }
    private fun ActivityMainBinding.render(post: Post) {
        post1 = post
        author.text = post.author
        published.text = post.published
        content.text = post.content
        like.setImageResource(getLikeIconResId(post.likedByMe))
    }
    @DrawableRes
    private fun getLikeIconResId(liked: Boolean) =
        if (liked) R.drawable.ic_heart_24 else R.drawable.ic_like_24
    }



