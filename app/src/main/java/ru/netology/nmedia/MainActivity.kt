package ru.netology.nmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет! Это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, аналитике и управлению. " +
                    "Мы растем и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остается с нами: мы верим, что в каждом уже есть сила, " +
                    "которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия помочь встать на путь роста и начать цепочку перемен",
            published = "21 мая в 18:36",
            likedByMe = false,
            )

        val funlife: FunLife = FunLife()


            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                if (post.likedByMe) {
                    like.setImageResource(R.drawable.ic_like_24)
                }
                binding.like.setOnClickListener {
                    post.likedByMe = !post.likedByMe
                    like.setImageResource(
                        if(post.likedByMe)
                            R.drawable.ic_heart_24 else R.drawable.ic_like_24
                    )
                    funlife.likeCount(numberLike)
                }
                binding.share.setOnClickListener {
                    funlife.shareCount(numberShare)
                }
                binding.eye.setOnClickListener {
                    funlife.eyeCount(numberEye)
                }
            }
       }
   }




