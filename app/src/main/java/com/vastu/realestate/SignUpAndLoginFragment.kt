package com.vastu.realestate

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.vastu.realestate.databinding.LoginSignUpViewBinding

class SignUpAndLoginFragment : Fragment() {


    private lateinit var viewModel: SignUpAndLoginViewModel
    lateinit var loginSignUpViewBinding:LoginSignUpViewBinding
    lateinit var viewPager: ViewPager2
    lateinit var tabLayout: TabLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginSignUpViewBinding = DataBindingUtil.inflate(inflater,R.layout.sign_up_and_login_fragment,container,false)
        viewPager = loginSignUpViewBinding.vpSignUpLogin
        tabLayout = loginSignUpViewBinding.tlLoginSignUpTab
        iniView()
        return loginSignUpViewBinding.root    }

    fun iniView(){
        viewPager.isUserInputEnabled = false
        val adapter = LoginPagerAdapter(parentFragmentManager, lifecycle)
        viewPager.adapter = adapter
        val dashboardTab = arrayOf(
            "Login",
            "Sign Up"
        )
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = dashboardTab[position]
        }.attach()


    }

}