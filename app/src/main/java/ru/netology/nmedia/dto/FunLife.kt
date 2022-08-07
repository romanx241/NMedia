package ru.netology.nmedia.dto

import android.widget.TextView

object FunLife {

    fun likeCount(numberLike: TextView, post: Post) {
        when(post.countLike.toInt()){
            in 0..999 -> usingJavaStringFormat(post.countLike, 0).also { numberLike.text = it }
            in 999..1100 -> (usingJavaStringFormat(post.countLike / 1000, 0) + "K").also { numberLike.text = it }
            in 1099..9999 -> (usingJavaStringFormat(post.countLike / 1000, 1) + "K").also { numberLike.text = it }
            in 9999..999999 -> (usingJavaStringFormat(post.countLike / 1000, 0) + "K").also { numberLike.text = it }
            in 999999..9999999 -> (usingJavaStringFormat(post.countLike / 1000000, 1) + "M").also { numberLike.text = it }
        }
    }
    fun shareCount(numberShare: TextView, post: Post) {
        when(post.countShare.toInt()){
            in 0..999 -> usingJavaStringFormat(post.countShare, 0).also { numberShare.text = it }
            in 999..1100 -> (usingJavaStringFormat(post.countShare / 1000, 0) + "K").also { numberShare.text = it }
            in 1099..9999 -> (usingJavaStringFormat(post.countShare / 1000, 1) + "K").also { numberShare.text = it }
            in 9999..999999 -> (usingJavaStringFormat(post.countShare / 1000, 0) + "K").also { numberShare.text = it }
            in 999999..9999999 -> (usingJavaStringFormat(post.countShare / 1000000, 1) + "M").also { numberShare.text = it }
        }
    }
    fun eyeCount(numberEye: TextView, post: Post) {
        when(post.countEye.toInt()){
            in 0..999 -> usingJavaStringFormat(post.countEye, 0).also { numberEye.text = it }
            in 999..1100 -> (usingJavaStringFormat(post.countEye / 1000, 0) + "K").also { numberEye.text = it }
            in 1099..9999 -> (usingJavaStringFormat(post.countEye / 1000, 1) + "K").also { numberEye.text = it }
            in 9999..999999 -> (usingJavaStringFormat(post.countEye / 1000, 0) + "K").also { numberEye.text = it }
            in 999999..9999999 -> (usingJavaStringFormat(post.countEye / 1000000, 1) + "M").also { numberEye.text = it }
        }
    }
    private fun usingJavaStringFormat(input: Double, scale: Int) = String.format("%.${scale}f", input)

}

