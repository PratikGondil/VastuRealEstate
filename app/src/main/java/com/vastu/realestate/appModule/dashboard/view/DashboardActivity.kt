package com.vastu.realestate.appModule.dashboard.view

import android.os.Bundle

import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.vastu.realestate.R
import com.vastu.realestate.appModule.enquiry.view.AddLoanEnquiryFragment
import com.vastu.realestate.appModule.enquiry.view.AddPropertyEnquiryFragment
import com.vastu.realestate.databinding.ActivityVastuDashboardBinding

class DashboardActivity : BaseActivity() {

    private lateinit var activityDashboardBinding: ActivityVastuDashboardBinding

    companion object{
        var userId: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDashboardBinding = DataBindingUtil.setContentView(this,R.layout.activity_vastu_dashboard)
        activityDashboardBinding.lifecycleOwner = this
    }

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.dashboardNavHost) as NavHostFragment?
        val fragment = navHostFragment!!.childFragmentManager.fragments[0]
        if (fragment is LoanFragment || fragment is RealEstateDetailsFragment) {
        if (fragment is LoanFragment || fragment is RealEstateDetailsFragment
            || fragment is AddLoanEnquiryFragment || fragment is AddPropertyEnquiryFragment) {
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