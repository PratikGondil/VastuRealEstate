package com.vastu.realestate.appModule.welcome

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.view.DashboardActivity
import com.vastu.realestate.databinding.WelcomeFragmentBinding


class WelcomeFragment : BaseFragment() {

    lateinit var welcomeFragmentBinding: WelcomeFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        welcomeFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.welcome_fragment, container, false)
        initView()
      return welcomeFragmentBinding.root
    }

    private fun initView() {
        Handler().postDelayed({
            callDashBoardActivity()
        }, 750)
    }

    fun callDashBoardActivity()
    {
        startActivity(Intent(activity, DashboardActivity::class.java))
    }

}