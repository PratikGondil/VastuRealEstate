package com.vastu.realestate.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vastu.realestate.R
import com.vastu.realestate.appModule.contactus.ContactUsViewModel
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.databinding.FragmentNotificationBinding


class NotificationFragment : BaseFragment(), IToolbarListener {


    lateinit var notificationViewModel: NotificationViewModel
    lateinit var drawerViewModel:DrawerViewModel
    lateinit var notificationBinding: FragmentNotificationBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationViewModel = ViewModelProvider(this)[NotificationViewModel::class.java]
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        notificationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification,container,false)
        notificationBinding.notificationViewModel = notificationViewModel
        drawerViewModel.iToolbarListener = this
        notificationBinding.drawerViewModel= drawerViewModel
        initView()
        return notificationBinding.root
    }

    private fun initView() {
        drawerViewModel.toolbarTitle.set(getString(R.string.notifications))
        drawerViewModel.isDashBoard.set(false)
    }

    override fun onClickBack() {
        findNavController().navigate(R.id.action_notificationFragment_to_Dashboard)

    }

    override fun onClickMenu() {

    }

    override fun onClickNotification() {

    }

}