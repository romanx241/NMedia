package ru.netology.nmedia

import android.widget.TextView

class FunLife {
    private var countLike: Double = 0.0
    private var countShare: Double = 0.0
    private var countEye: Double = 0.0

    fun likeCount(numberLike: TextView) {
        countLike++
        when(countLike.toInt()){
            in 0..999 -> usingJavaStringFormat(countLike, 0).also { numberLike.text = it }
            in 999..1100 -> (usingJavaStringFormat(countLike / 1000, 0) + "K").also { numberLike.text = it }
            in 1099..9999 -> (usingJavaStringFormat(countLike / 1000, 1) + "K").also { numberLike.text = it }
            in 9999..999999 -> (usingJavaStringFormat(countLike / 1000, 0) + "K").also { numberLike.text = it }
            in 999999..9999999 -> (usingJavaStringFormat(countLike / 1000000, 1) + "M").also { numberLike.text = it }
        }
    }

    fun shareCount(numberShare: TextView) {
        countShare++
        when(countShare.toInt()){
            in 0..999 -> usingJavaStringFormat(countShare, 0).also { numberShare.text = it }
            in 999..1100 -> (usingJavaStringFormat(countShare / 1000, 0) + "K").also { numberShare.text = it }
            in 1099..9999 -> (usingJavaStringFormat(countShare / 1000, 1) + "K").also { numberShare.text = it }
            in 9999..999999 -> (usingJavaStringFormat(countShare / 1000, 0) + "K").also { numberShare.text = it }
            in 999999..9999999 -> (usingJavaStringFormat(countShare / 1000000, 1) + "M").also { numberShare.text = it }
        }
    }

    fun eyeCount(numberEye: TextView) {
        countEye++
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