package com.vastu.realestate

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vastu.realestate.appModule.signUp.view.SignUpFragment
import com.vastu.realestate.appModule.login.view.fragment.LoginFragment

private const val NUM_TABS = 2

class LoginPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0->
            {
                fragment = LoginFragment()

            }
            1-> {
                fragment = SignUpFragment()
            }

        }
        return fragment!!
    }

}