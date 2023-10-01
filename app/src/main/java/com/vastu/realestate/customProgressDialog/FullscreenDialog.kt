package com.vastu.realestate.customProgressDialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import com.vastu.realestate.R

@SuppressLint("MissingInflatedId")
class FullScreenDialog(context: Context, videoUrl: String) : Dialog(context) {

    init {
        // Set the custom dialog layout
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_video_dialog)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        // Initialize VideoView and ImageView
        val videoView = findViewById<com.potyvideo.library.AndExoPlayerView>(R.id.andExoPlayerViewType)
        val closeImageView = findViewById<ImageView>(R.id.img_cross)
        videoView.setSource(videoUrl)
        closeImageView.setOnClickListener {
            dismiss()
        }


    }
}
