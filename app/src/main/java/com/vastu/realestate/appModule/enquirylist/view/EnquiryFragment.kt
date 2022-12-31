package com.vastu.realestate.appModule.enquirylist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.vastu.realestate.LoginPagerAdapter
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.enquirylist.adapter.EnquiryPagerAdapter
import com.vastu.realestate.databinding.FragmentEnquiryBinding

class EnquiryFragment : BaseFragment(), IToolbarListener {

    private lateinit var enquiryBinding:FragmentEnquiryBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var drawerViewModel: DrawerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        enquiryBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_enquiry, container, false)
        viewPager = enquiryBinding.viewPagerEnquiry
        tabLayout = enquiryBinding.tabLayoutEnquiry
        enquiryBinding.lifecycleOwner = this
        drawerViewModel.iToolbarListener = this
        enquiryBinding.drawerViewModel = drawerViewModel
        initView()
        return enquiryBinding.root
    }

    override fun onResume() {
        super.onResume()
        drawerViewModel.toolbarTitle.set(getString(R.string.enquiry))
        drawerViewModel.isDashBoard.set(false)
    }

    private fun initView(){
        viewPager.isUserInputEnabled = false
        val adapter = EnquiryPagerAdapter(parentFragmentManager, lifecycle)
        viewPager.adapter = adapter
        val dashboardTab = arrayOf(
            "Loan",
            "Property"
        )
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = dashboardTab[position]
        }.attach()
    }
    override fun onClickBack() {
       activity?.finish()
    }

    override fun onClickMenu() {
        //onClickMenu
    }

    override fun onClickNotification() {
        //onClickNotification
    }
}