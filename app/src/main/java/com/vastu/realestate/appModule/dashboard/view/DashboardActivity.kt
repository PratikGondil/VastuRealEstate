package com.vastu.realestate.appModule.dashboard.view

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.viewmodel.DashboardViewModel
import com.vastu.realestate.databinding.ActivityVastuDashboardBinding

class DashboardActivity : BaseActivity() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var activityDashboardBinding: ActivityVastuDashboardBinding

    companion object{
        var userId: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        activityDashboardBinding = DataBindingUtil.setContentView(this,R.layout.activity_vastu_dashboard)
        activityDashboardBinding.lifecycleOwner = this
        activityDashboardBinding.dashboardViewModel = dashboardViewModel
    }

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.dashboardNavHost) as NavHostFragment?
        val fragment = navHostFragment!!.childFragmentManager.fragments[0]
        if (fragment is LoanFragment || fragment is RealEstateDetailsFragment) {
            super.onBackPressed()
        }
        else if( fragment is RealEstateFragment)
        {
            fragment.onClickBack()
        }else {
            finishAffinity()
        }
    }

}