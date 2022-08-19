package ru.netology.nmedia.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.SingleLiveEvent


class PostViewModel: ViewModel(), PostInteractionListener {

    private val repository: PostRepository = InMemoryPostRepository()
    val data  = repository.getAll()
    private val currentPost = MutableLiveData<Post?>(null)
    val sharePostContent = SingleLiveEvent<String>()
    val navigatePostContentScreenEvent = SingleLiveEvent<Unit>()
    val editPostContent = SingleLiveEvent<String>()
    val playVideoContent = SingleLiveEvent<String>()


    override fun onLikeClicked(post: Post) = repository.like(post.id)

    override fun onRemoveClicked(post: Post) = repository.delete(post.id)

    override fun onEditClicked(post: Post) { currentPost.value = post }

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
