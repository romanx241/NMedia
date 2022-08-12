package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyBoard
import ru.netology.nmedia.util.showKeyBoard
import ru.netology.nmedia.viewModel.PostViewModel


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(
            interactionListener = viewModel,
            shareCount = { post -> viewModel.shareCount(post) },
            eyeCount = { post -> viewModel.eyeCount(post) })
        binding.postRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        binding.saveButton.setOnClickListener {
            with(binding.contentEditText) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)
            }
        }
        viewModel.currentPost.observe(this) { currentPost ->
            with(binding.contentEditText) {
                val content = currentPost?.content
                setText(content)
                if (content != null) {
                    requestFocus()
                    showKeyBoard()
                } else {
                    clearFocus()
                    hideKeyBoard()
                }
            }
        }
        with(binding.group) {
            group.visibility = View.GONE
            contentEditText.setOnClickListener {
                cancel.visibility = View.VISIBLE
                pencil.visibility = View.VISIBLE
            }
        }
    }
}


