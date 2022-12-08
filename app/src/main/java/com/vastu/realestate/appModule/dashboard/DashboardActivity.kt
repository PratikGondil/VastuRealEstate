package com.vastu.realestate.rootModule.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.VastuDashboardFragment
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyDtls
import com.vastu.realestate.databinding.ActivityVastuDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var dashboardBinding: ActivityVastuDashboardBinding
    private lateinit var objVerifyDtls:ObjVerifyDtls

    companion object{
        val USER_DETAILS = "USER_DETAILS"
        fun newIntent(context: Context?, objVerifyDtls: ObjVerifyDtls?): Intent? {
            var intent = Intent(context,DashboardActivity::class.java)
            intent.putExtra(USER_DETAILS,objVerifyDtls)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_vastu_dashboard);
        dashboardBinding.apply {
           
       }
    }

    private fun openDrawer(){
        dashboardBinding.drawerLayout.openDrawer(GravityCompat.START);
    }
    private fun closeDrawer(){
        dashboardBinding.drawerLayout.closeDrawer(GravityCompat.START);
    }
    override fun onBackPressed() {
        val navHostFragment: NavHostFragment? =
            supportFragmentManager.findFragmentById(R.id.dashboardNavHost) as NavHostFragment?
        val fragment = navHostFragment!!.childFragmentManager.fragments[0]
        if (fragment is VastuDashboardFragment)
            finishAffinity()
        else
            onBackPressed()
    }
}