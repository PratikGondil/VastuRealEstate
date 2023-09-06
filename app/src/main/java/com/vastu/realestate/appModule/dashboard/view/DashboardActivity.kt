package com.vastu.realestate.appModule.dashboard.view

import android.os.Bundle

import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.view.bottomnav.PrimePropertiesFragment
import com.vastu.realestate.appModule.dashboard.view.offers.OffersFragment
import com.vastu.realestate.appModule.employee.fragment.EmployeeDetailsFragment
import com.vastu.realestate.appModule.employee.fragment.EmployeeListFragment
import com.vastu.realestate.appModule.enquiry.view.AddLoanEnquiryFragment
import com.vastu.realestate.appModule.enquiry.view.AddPropertyEnquiryFragment
import com.vastu.realestate.appModule.properties.view.PropertiesFragment

import com.vastu.realestate.databinding.ActivityVastuDashboardBinding


class DashboardActivity : BaseActivity() {
    private lateinit var activityDashboardBinding: ActivityVastuDashboardBinding

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDashboardBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_vastu_dashboard)
        activityDashboardBinding.lifecycleOwner = this

        bottomNavigationView = findViewById(R.id.bottomNavigation_dashboard)
        navController = Navigation.findNavController(this, R.id.dashboardNavHost)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onBackPressed() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.dashboardNavHost) as NavHostFragment?
        val fragment = navHostFragment!!.childFragmentManager.fragments[0]
        if (fragment is LoanFragment || fragment is PrimePropertiesFragment || fragment is RealEstateDetailsFragment
            || fragment is AddLoanEnquiryFragment || fragment is AddPropertyEnquiryFragment
            || fragment is AddPropertyFragment || fragment is OffersFragment
            || fragment is PropertiesFragment || fragment is AddPropertyFragment || fragment is OffersFragment || fragment is EmployeeDetailsFragment || fragment is EmployeeListFragment
        ) {

            super.onBackPressed()
        } else {
            finishAffinity()
        }
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
                item ->
            when(item.itemId){
                R.id.PropertiesFragment ->
                    isPrimeClicked()
                R.id.RealEstateFragment->
                    isUpcomingClicked()
                }
            NavigationUI.onNavDestinationSelected(
                item,
                navController
            )
        }

    fun isPrimeClicked(){
        isPrime = true
        isUpcomingClicked= false
    }

    fun isUpcomingClicked(){
        isPrime = false
        isUpcomingClicked= true
    }

    companion object{
        var isUpcomingClicked = false
        var isPrime = false
    }
}