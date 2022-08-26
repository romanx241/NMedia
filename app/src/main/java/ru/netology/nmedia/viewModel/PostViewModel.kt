package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.NewRepository
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.SingleLiveEvent


class PostViewModel(application: Application): AndroidViewModel(application), PostInteractionListener {

     private val repository: PostRepository = NewRepository(application)
    val data = repository.getAll()
    val currentPost = MutableLiveData<Post?>(null)
    val sharePostContent = SingleLiveEvent<String>()
    val navigatePostContentScreenEvent = SingleLiveEvent<String>()
    val editPostContent = SingleLiveEvent<String>()
    val playVideoContent = SingleLiveEvent<String>()
    val navigateToPostDetails = SingleLiveEvent<Long>()

    override fun onLikeClicked(post: Post) = repository.like(post.id)

    override fun onRemoveClicked(post: Post) = repository.delete(post.id)

    override fun onEditClicked(post: Post) { currentPost.value = post
        navigatePostContentScreenEvent.value = post.content
    }

    override fun onShareClicked(post: Post) { sharePostContent.value = post.content }

    fun onAddClicked() {navigatePostContentScreenEvent.call()}

    fun shareCount(post: Post) = repository.share(post.id)

    fun eyeCount(post: Post) = repository.eye(post.id)


    fun onSaveButtonClicked(content: String){
        if(content.isBlank()) return
        val post = currentPost.value?.copy(
            content = content
        )?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Roman",
            content = content,
            published = "Today",
            countLike = 990.0,
            countShare = 990.0,
            countEye = 990.0,
            videoUrl = null
        )
        repository.save(post)
        currentPost.value = null
    }
    override fun onVideoClicked(post: Post) {
        post.videoAttachment.let {
            playVideoContent.value = it.toString()
        }
    }
}
