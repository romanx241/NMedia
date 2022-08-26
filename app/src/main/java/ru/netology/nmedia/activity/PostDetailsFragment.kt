package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.dto.FunLife
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewModel.PostViewModel

class PostDetailsFragment : Fragment() {


    private val viewModel by viewModels<PostViewModel>(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentPostBinding.inflate(layoutInflater, container, false).also { binding ->

        val post = viewModel.currentPost.value

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val sortedPosts = posts.filter { it.id == post?.id }
            if (sortedPosts.isNotEmpty()) {
                binding.render(sortedPosts.first())
            } else {
                findNavController().navigateUp()
            }
        }

        setFragmentResultListener(
            requestKey = NewPostFragment.REQUEST_KEY
        ) { requestKey, bundle ->
            if (requestKey != NewPostFragment.REQUEST_KEY) return@setFragmentResultListener
            val newPostContent = bundle.getString(
                NewPostFragment.RESULT_KEY
            ) ?: return@setFragmentResultListener
            viewModel.onSaveButtonClicked(newPostContent)
        }

        viewModel.sharePostContent.observe(viewLifecycleOwner) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        viewModel.playVideoContent.observe(viewLifecycleOwner) { postUrl ->
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(postUrl)
            }
            startActivity(intent)
        }

        post?.let {
            binding.like.setOnClickListener {
                with(viewModel) { onLikeClicked(post) }
            }

            binding.share.setOnClickListener {
                with(viewModel) { onShareClicked(post) }
            }

            binding.videoFrameInPost.videoPoster.setOnClickListener {
                with(viewModel) { onVideoClicked(post) }
            }

            val popupMenu by lazy {
                PopupMenu(layoutInflater.context, binding.options).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.remove -> {
                                with(viewModel) { onRemoveClicked(post) }
                                true
                            }
                            R.id.edit -> {
                                with(viewModel) { onEditClicked(post) }
                                true
                            }
                            else -> false
                        }
                    }
                }
            }

            binding.options.setOnClickListener { popupMenu.show() }
        }

    }.root

    private fun FragmentPostBinding.render(post: Post) {

        post.let {
            avatar.setImageResource(R.drawable.ic_avatar_24)
            author.text = post.author
            published.text = post.published
            content.text = post.content
            like.text = post.likes.toString()
            like.isChecked = post.likedByMe
            FunLife.onLikeClicked(like, post)
            FunLife.shareCount(share, post)
            FunLife.eyeCount(eye, post)

            if (post.videoUrl != null) {
                videoFrameInPost.root.visibility = View.VISIBLE
                videoFrameInPost.videoUrl.text = post.videoUrl
            } else {
                videoFrameInPost.root.visibility = View.GONE
            }
        }
    }
}


