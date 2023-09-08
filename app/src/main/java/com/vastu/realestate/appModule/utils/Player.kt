package com.vastu.realestate.appModule.utils

import android.content.Context
import android.media.MediaPlayer
import android.util.AttributeSet
import android.widget.VideoView

class Player(context: Context?, attributes: AttributeSet?) :
    VideoView(context, attributes), MediaPlayer.OnPreparedListener,
    MediaPlayer.OnCompletionListener,
    MediaPlayer.OnErrorListener {
    private var mediaPlayer: MediaPlayer? = null

    init {
        setOnPreparedListener(this)
        setOnCompletionListener(this)
        setOnErrorListener(this)
    }

    override fun onPrepared(mediaPlayer: MediaPlayer) {
        this.mediaPlayer = mediaPlayer
    }

    override fun onError(mediaPlayer: MediaPlayer, what: Int, extra: Int): Boolean {
        return false
    }

    override fun onCompletion(mediaPlayer: MediaPlayer) {}
    fun mute() {
        setVolume(0)
    }

    fun unmute() {
        setVolume(100)
    }

    private fun setVolume(amount: Int) {
        val max = 100
        val numerator: Double = if (max - amount > 0) Math.log((max - amount).toDouble()) else 0.0
        val volume = (1 - numerator / Math.log(max.toDouble())).toFloat()
        mediaPlayer!!.setVolume(volume, volume)
    }
}
