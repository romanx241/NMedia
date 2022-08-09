package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    val viewModel by viewModels<PostViewModel>()
    val adapter = PostAdapter(
        likeCount = { post ->
            viewModel.likeCount(post)
        },
        shareCount = { post ->
            viewModel.shareCount(post)
        },
        eyeCount = { post ->
            viewModel.eyeCount(post)
        }
    )
    binding.postRecyclerView.adapter = adapter
    viewModel.data.observe(this) { posts ->
        adapter.submitList(posts)
        }
    }
}


