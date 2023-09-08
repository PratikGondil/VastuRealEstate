package com.vastu.realestate.appModule.video

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.vastu.realestate.R
import com.vastu.realestate.appModule.selectlanguage.SelectLanguageViewModel
import com.vastu.realestate.databinding.FragmentVideoBinding

class VideoFragment : Fragment() {

    companion object {
        fun newInstance() = VideoFragment()
    }

    private lateinit var viewModel: VideoViewModel
    lateinit var videoBinding: FragmentVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[VideoViewModel::class.java]
        videoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_video,container,false)
        videoBinding.videoViewModel = viewModel
        initView()
        return videoBinding.root
    }

    private fun initView() {
     //  videoBinding.andExoPlayerView.setSource("https://myclanservices.co.in/pratik/video.mp4")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VideoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}