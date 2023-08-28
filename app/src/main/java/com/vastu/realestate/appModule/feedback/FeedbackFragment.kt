package com.vastu.realestate.appModule.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.databinding.FeedbackFragmentBinding

class FeedbackFragment :BaseFragment(), IToolbarListener {

    lateinit var feedbackViewModel: FeedbackViewModel
    lateinit var drawerViewModel: DrawerViewModel
    lateinit var feedbackFragmentBinding: FeedbackFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        feedbackViewModel = ViewModelProvider(this)[FeedbackViewModel::class.java]
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        feedbackFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.feedback_fragment,container,false)
        feedbackFragmentBinding.feedbackViewModel = feedbackViewModel
        drawerViewModel.iToolbarListener = this
        feedbackFragmentBinding.drawerViewModel= drawerViewModel
        initView()
        return feedbackFragmentBinding.root
    }

    private fun initView() {
        drawerViewModel.toolbarTitle.set(getString(R.string.feedback))
        drawerViewModel.isDashBoard.set(false)
    }

    override fun onClickBack() {
        findNavController().navigate(R.id.action_feedback_to_Dashboard)
    }

    override fun onClickMenu() {

    }

    override fun onClickNotification() {

    }



}