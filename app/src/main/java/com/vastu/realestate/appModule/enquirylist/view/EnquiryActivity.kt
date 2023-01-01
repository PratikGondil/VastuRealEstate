package com.vastu.realestate.appModule.enquirylist.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.BaseActivity
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.databinding.ActivityEnquiryBinding

class EnquiryActivity : BaseActivity() {
    private lateinit var enquiryBinding: ActivityEnquiryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enquiryBinding = DataBindingUtil.setContentView(this, R.layout.activity_enquiry);
        enquiryBinding.lifecycleOwner = this
    }

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.enquiryNavHost) as NavHostFragment?
        val fragment = navHostFragment!!.childFragmentManager.fragments[0]
        if(fragment is PropertyEnquiryListFragment){
           findNavController(R.id.PropertyEnquiryListFragment).navigate(R.id.action_PropertyEnquiryListFragment_to_LoanEnquiryListFragment)
        }else if (fragment is LoanEnquiryListFragment || fragment is EnquiryFragment)
           finish()
           //super.onBackPressed()
        }
}