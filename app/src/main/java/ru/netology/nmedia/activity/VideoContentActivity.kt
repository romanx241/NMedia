package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.VideoContentActivityBinding

class VideoContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = VideoContentActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

        }



