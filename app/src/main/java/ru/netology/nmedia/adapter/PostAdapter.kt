package ru.netology.nmedia.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostListItemBinding
import ru.netology.nmedia.dto.FunLife
import ru.netology.nmedia.dto.Post

typealias OnLikeListener = (Post) -> Unit
typealias OnShareListener = (Post) -> Unit
typealias OnEyeListener = (Post) -> Unit

class PostAdapter(

    private val likeCount: OnLikeListener,
    private val shareCount: OnShareListener,
    private val eyeCount: OnEyeListener

) : ListAdapter<Post, PostAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("PostAdapter", "onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, likeCount, shareCount, eyeCount)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("PostAdapter", "onBindViewHolder: $position")
        val post = getItem(position)
        holder.bind(post)
    }

    class ViewHolder(
        private val binding: PostListItemBinding,
        private val likeCount: OnLikeListener,
        private val shareCount: OnShareListener,
        private val eyeCount: OnEyeListener,
        private val funLife: FunLife = FunLife
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post
        init {
            binding.like.setOnClickListener {
                likeCount(post)
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
                funLife.likeCount(numberLike, post)
                funLife.shareCount(numberShare, post)
                funLife.eyeCount(numberEye, post)
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



