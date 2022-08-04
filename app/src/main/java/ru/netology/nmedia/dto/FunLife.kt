package ru.netology.nmedia.dto

import android.widget.TextView

object FunLife {


    fun likeCount(numberLike: TextView, countLike: Double) {
        countLike
        when(countLike.toInt()){
            in 0..999 -> usingJavaStringFormat(countLike, 0).also { numberLike.text = it }
            in 999..1100 -> (usingJavaStringFormat(countLike / 1000, 0) + "K").also { numberLike.text = it }
            in 1099..9999 -> (usingJavaStringFormat(countLike / 1000, 1) + "K").also { numberLike.text = it }
            in 9999..999999 -> (usingJavaStringFormat(countLike / 1000, 0) + "K").also { numberLike.text = it }
            in 999999..9999999 -> (usingJavaStringFormat(countLike / 1000000, 1) + "M").also { numberLike.text = it }
               }
    }

    fun shareCount(numberShare: TextView, countShare: Double) {
        countShare
        when(countShare.toInt()){
            in 0..999 -> usingJavaStringFormat(countShare, 0).also { numberShare.text = it }
            in 999..1100 -> (usingJavaStringFormat(countShare / 1000, 0) + "K").also { numberShare.text = it }
            in 1099..9999 -> (usingJavaStringFormat(countShare / 1000, 1) + "K").also { numberShare.text = it }
            in 9999..999999 -> (usingJavaStringFormat(countShare / 1000, 0) + "K").also { numberShare.text = it }
            in 999999..9999999 -> (usingJavaStringFormat(countShare / 1000000, 1) + "M").also { numberShare.text = it }
        }
    }

    fun eyeCount(numberEye: TextView, countEye: Double) {
        countEye
        when(countEye.toInt()){
            in 0..999 -> usingJavaStringFormat(countEye, 0).also { numberEye.text = it }
            in 999..1100 -> (usingJavaStringFormat(countEye / 1000, 0) + "K").also { numberEye.text = it }
            in 1099..9999 -> (usingJavaStringFormat(countEye / 1000, 1) + "K").also { numberEye.text = it }
            in 9999..999999 -> (usingJavaStringFormat(countEye / 1000, 0) + "K").also { numberEye.text = it }
            in 999999..9999999 -> (usingJavaStringFormat(countEye / 1000000, 1) + "M").also { numberEye.text = it }
        }
    }
    private fun usingJavaStringFormat(input: Double, scale: Int) = String.format("%.${scale}f", input)
}