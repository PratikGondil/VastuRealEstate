package com.vastu.realestate.appModule.ourServies.modeForAdvertisement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.DashboardActivity
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.databinding.ModeForAdvertisementFragmentBinding

class ModeForAdvertisementFragment: Fragment(), IToolbarListener, IModeForAdvertisementListener {
    lateinit var modeForAdvertisementViewModel: ModeForAdvertisementViewModel
    lateinit var drawerViewModel: DrawerViewModel
    lateinit var modeForAdvertisementFragmentBinding: ModeForAdvertisementFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        modeForAdvertisementViewModel = ViewModelProvider(this)[ModeForAdvertisementViewModel::class.java]
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        modeForAdvertisementFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.mode_for_advertisement_fragment, container, false)
        modeForAdvertisementFragmentBinding.modeForAdvertisementViewModel = modeForAdvertisementViewModel
        drawerViewModel.iToolbarListener = this
        modeForAdvertisementViewModel.iModeForAdvertisementListener = this
        modeForAdvertisementFragmentBinding.drawerViewModel = drawerViewModel
        initView()
        return modeForAdvertisementFragmentBinding.root
    }

    fun initView() {
        if(activity is DashboardActivity)
        {
            (activity as DashboardActivity).bottomNavigationView.visibility= View.GONE
        }
        drawerViewModel.toolbarTitle.set(getString(R.string.plan_for_advertisement))
        drawerViewModel.isDashBoard.set(false)
    }

    override fun onClickBack() {
        TODO("Not yet implemented")
    }

    override fun onClickMenu() {
        TODO("Not yet implemented")
    }

    override fun onClickNotification() {
        TODO("Not yet implemented")
    }

    override fun onPlanVedioAdvertisementClick() {

    }

    override fun onPlanBannerAdvertisementClick() {
        TODO("Not yet implemented")
    }
}