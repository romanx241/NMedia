package ru.netology.nmedia.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.CustomPopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostListItemBinding
import ru.netology.nmedia.dto.FunLife
import ru.netology.nmedia.dto.Post

typealias OnShareListener = (Post) -> Unit
typealias OnEyeListener = (Post) -> Unit

internal class PostAdapter(

    private val interactionListener: PostInteractionListener,
    private val shareCount: OnShareListener,
    private val eyeCount: OnEyeListener

    ) : ListAdapter<Post, PostAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("PostAdapter", "onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener, shareCount, eyeCount)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("PostAdapter", "onBindViewHolder: $position")
        val post = getItem(position)
        holder.bind(post)
    }

    class ViewHolder(
        private val binding: PostListItemBinding,
        listener: PostInteractionListener,
        private val shareCount: OnShareListener,
        private val eyeCount: OnEyeListener,
        private val funLife: FunLife = FunLife
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        private val popupMenu by lazy {
            CustomPopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when(menuItem.itemId){
                        R.id.remove -> {
                            listener.onRemoveClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.like.setOnClickListener {
                listener.onLikeClicked(post)
            }
            binding.share.setOnClickListener {
                listener.onShareClicked(post)
                shareCount(post)
            }
            binding.eye.setOnClickListener {
                eyeCount(post)
            }
            binding.videoFrameInPost.videoPoster.setOnClickListener {
                listener.onVideoClicked(post)
            }

            binding.options.setOnClickListener { popMenu()
            }
            binding.options.setOnClickListener{ popupMenu.show()
            }
        }

        private fun popMenu() {
            if(post.content.isNotBlank()) {
                popupMenu.menu.add(0, R.id.edit, Menu.NONE, itemView.context.getString(R.string.edit)).apply {
                    setIcon(R.drawable.ic_baseline_edit_24)
                }
            }
            popupMenu.menu.add(0, R.id.remove, Menu.NONE, itemView.context.getString(R.string.remove)).apply {
                setIcon(R.drawable.ic_baseline_delete_24)
            }
        }

        fun bind(post: Post) {
            this.post = post
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                like.text = post.likes.toString()
                like.isChecked = post.likedByMe
                funLife.onLikeClicked(like, post)
                funLife.shareCount(share, post)
                funLife.eyeCount(eye, post)
                if (post.videoUrl != null) {
                    videoFrameInPost.root.visibility = View.VISIBLE
                    videoFrameInPost.videoUrl.text = post.videoUrl
                } else {
                    videoFrameInPost.root.visibility = View.GONE
                }
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}



