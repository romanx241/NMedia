package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        run {
//            val preferences = getPreferences(Context.MODE_PRIVATE)
//            preferences.edit {
//                putString("key", "value")
//            }
//        }
//        run {
//            val preferences = getPreferences(Context.MODE_PRIVATE)
//            val value = preferences.getString("key", "no value") ?: return@run
//            Snackbar.make(binding.root, value, Snackbar.LENGTH_INDEFINITE).show()
//
//        }

        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(
            interactionListener = viewModel,
            shareCount = { post -> viewModel.shareCount(post) },
            eyeCount = { post -> viewModel.eyeCount(post) })
        binding.postRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        binding.fab.setOnClickListener {
            viewModel.onAddClicked()
        }

        viewModel.sharePostContent.observe(this){postContent ->
            val intent = Intent().apply{
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent =
                Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }
        viewModel.playVideoContent.observe(this) {
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse("https://www.youtube.com/watch?v=WhWc3b3KhnY")
            }
            startActivity(intent)
        }

        val postContentActivityLauncher = registerForActivityResult(
            PostContentActivity.ResultContract
        ){ postContent ->
            postContent ?: return@registerForActivityResult
            viewModel.onSaveButtonClicked(postContent)
        }
        viewModel.navigatePostContentScreenEvent.observe(this){
        binding.fab.setOnClickListener { postContentActivityLauncher.launch() }
        }
        val editContentLauncher = registerForActivityResult (
            EditContentActivity.EditContentResultContract
        ){ postContent ->
            postContent ?: return@registerForActivityResult
        }
        viewModel.editPostContent.observe(this){
            editContentLauncher.launch()
        }

    }
}


