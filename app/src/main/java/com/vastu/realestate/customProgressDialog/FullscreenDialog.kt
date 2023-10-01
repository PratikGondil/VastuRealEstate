package com.vastu.realestate.customProgressDialog

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import com.vastu.realestate.R

class FullScreenDialog(context: Context, videoUrl: String) : Dialog(context) {

    init {
        // Set the custom dialog layout
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_video_dialog)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        // Initialize VideoView and ImageView
        val videoView = findViewById<VideoView>(R.id.andExoPlayerViewType)
        val closeImageView = findViewById<ImageView>(R.id.img_cross)

        // Set the video URI (assuming you have the video file path or URL)
        val mediaController = MediaController(context)
        mediaController.setAnchorView(videoView)
        mediaController.setMediaPlayer(videoView)
        videoView.setMediaController(mediaController)
        videoView.setVideoURI(Uri.parse(videoUrl))
        videoView.requestFocus()
        // Start playing the video
        videoView.start()
        mediaController.show()
        mediaController.isEnabled = true

        closeImageView.setOnClickListener {
            // Stop the video and dismiss the dialog when the close button is clicked
            videoView.stopPlayback()
            dismiss()
        }

        // Set media controller's previous, play/pause, and next buttons' functionality
        mediaController.setPrevNextListeners(
            { /* Previous button click listener */ },
            { /* Next button click listener */ }
        )

    }
}
