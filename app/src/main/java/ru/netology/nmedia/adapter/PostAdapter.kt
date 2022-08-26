package ru.netology.nmedia.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
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
            PopupMenu(itemView.context, binding.options).apply {
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
                shareCount(post)
            }
            binding.eye.setOnClickListener {
                eyeCount(post)
            }
        }
        fun bind(post: Post) {
            this.post = post
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
               like.setImageResource(
                    if (post.likedByMe)
                        R.drawable.ic_heart_24 else R.drawable.ic_like_24
                )
                funLife.onLikeClicked(numberLike, post)
                funLife.shareCount(numberShare, post)
                funLife.eyeCount(numberEye, post)
                options.setOnClickListener{ popupMenu.show() }
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



