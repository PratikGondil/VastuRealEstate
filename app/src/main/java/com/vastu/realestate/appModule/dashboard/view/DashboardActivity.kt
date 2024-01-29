package com.vastu.realestate.appModule.dashboard.view

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vastu.networkService.util.Constants
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.view.bottomnav.PrimePropertiesFragment
import com.vastu.realestate.appModule.dashboard.view.offers.OffersFragment
import com.vastu.realestate.appModule.employee.fragment.EmployeeDetailsFragment
import com.vastu.realestate.appModule.employee.fragment.EmployeeListFragment
import com.vastu.realestate.appModule.enquiry.view.AddLoanEnquiryFragment
import com.vastu.realestate.appModule.enquiry.view.AddPropertyEnquiryFragment
import com.vastu.realestate.appModule.properties.view.PropertiesFragment
import com.vastu.realestate.appModule.realCreator.creatorDetails.CreatorDetailsFragment
import com.vastu.realestate.appModule.realCreator.creatorsList.CreatorListFragment
import com.vastu.realestate.appModule.realCreator.infoPage.FindProfileFragment
import com.vastu.realestate.databinding.ActivityVastuDashboardBinding
import com.vastu.realestate.utils.PreferenceManger


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
        showTermsDialog()
    }

    private fun showTermsDialog() {
        var isAccepted = PreferenceManger.getBoolean<Boolean>(Constants.TERMS_ACCEPTED)
        if(isAccepted!!) {
            createDialog()
        }
    }

    override fun onBackPressed() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.dashboardNavHost) as NavHostFragment?
        val fragment = navHostFragment!!.childFragmentManager.fragments[0]
        if (fragment is RealEstateFragment || fragment is LoanFragment || fragment is PrimePropertiesFragment || fragment is RealEstateDetailsFragment
            || fragment is AddLoanEnquiryFragment || fragment is AddPropertyEnquiryFragment
            || fragment is AddPropertyFragment || fragment is OffersFragment
            || fragment is PropertiesFragment || fragment is AddPropertyFragment
            || fragment is OffersFragment || fragment is EmployeeDetailsFragment
            || fragment is EmployeeListFragment || fragment is FindProfileFragment
            || fragment is CreatorListFragment || fragment is CreatorDetailsFragment
        ) {

            super.onBackPressed()
        } else {
            finishAffinity()
        }
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
                item ->
            NavigationUI.onNavDestinationSelected(
                item,
                navController
            )
        }
    fun createDialog() {
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.custom_dialog,null)
        val  checkbox = view.findViewById<CheckBox>(R.id.termsCheck)
        val cross=view.findViewById<ImageView>(R.id.img_cross)
        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 30)
        builder.getWindow()!!.setBackgroundDrawable(inset)
        builder.setView(view)
        checkbox.setOnClickListener {
            builder.dismiss()
            PreferenceManger.putBoolean<Boolean>(Constants.TERMS_ACCEPTED, true)
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
        cross.setOnClickListener{
            builder.dismiss()
        }
    }
}