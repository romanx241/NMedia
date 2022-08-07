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

    private lateinit var post : Post
    private val viewModel by viewModels<PostViewModel>()
    private val funLife: FunLife = FunLife


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { post ->
            binding.render(post)
        }
        binding.like.setOnClickListener {
            viewModel.likeCount()
            funLife.likeCount(numberLike, post)
        }
        binding.share.setOnClickListener {
            viewModel.shareCount()
            funLife.shareCount(numberShare, post)
        }
        binding.eye.setOnClickListener {
            viewModel.eyeCount()
            funLife.eyeCount(numberEye, post)
        }
    }
    private fun ActivityMainBinding.render(post: Post) {
         this@MainActivity.post = post
        author.text = post.author
        published.text = post.published
        content.text = post.content
        like.setImageResource(getLikeIconResId(post.likedByMe))
    }

    @DrawableRes
    private fun getLikeIconResId(liked: Boolean) =
        if (liked) R.drawable.ic_heart_24 else R.drawable.ic_like_24
    }



