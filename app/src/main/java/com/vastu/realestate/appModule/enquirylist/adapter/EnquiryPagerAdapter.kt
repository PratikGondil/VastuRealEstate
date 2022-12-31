package com.vastu.realestate.appModule.enquirylist.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vastu.realestate.appModule.enquirylist.view.LoanEnquiryListFragment
import com.vastu.realestate.appModule.enquirylist.view.PropertyEnquiryListFragment

private const val NUM_TABS = 2

class EnquiryPagerAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = LoanEnquiryListFragment()
            }
            1 -> {
                fragment = PropertyEnquiryListFragment()
            }
        }
        return fragment!!
    }
}
