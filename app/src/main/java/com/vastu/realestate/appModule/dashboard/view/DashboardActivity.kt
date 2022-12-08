package com.vastu.realestate.appModule.dashboard.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.callback.IDashboardViewListener
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyDtls

class DashboardActivity : AppCompatActivity(),IDashboardViewListener {

    private lateinit var objVerifyDtls:ObjVerifyDtls
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var tvContactUs : LinearLayout
    private lateinit var tvSettings : LinearLayout
    private lateinit var tvLogout : LinearLayout
    private lateinit var tvUsername:TextView
    private lateinit var tvMobile:TextView
    private lateinit var closeImage:ImageView

    companion object{
        val USER_DETAILS = "USER_DETAILS"
        fun newIntent(context: Context?, objVerifyDtls: ObjVerifyDtls?): Intent? {
            var intent = Intent(context, DashboardActivity::class.java)
            intent.putExtra(USER_DETAILS,objVerifyDtls)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vastu_dashboard)
        initNavigationMenus()
        drawerLayout = findViewById(R.id.drawer_layout)
    }
    private fun initNavigationMenus(){
        objVerifyDtls = intent.getSerializableExtra(USER_DETAILS) as ObjVerifyDtls;
        tvContactUs = findViewById(R.id.nav_contact_us)
        tvSettings = findViewById(R.id.nav_settings)
        tvLogout = findViewById(R.id.nav_log_out)
        tvUsername = findViewById(R.id.username_textview)
        tvMobile= findViewById(R.id.mobile_textview)
        closeImage = findViewById(R.id.close_imageview)
        if(objVerifyDtls!=null) {
            tvMobile.text = objVerifyDtls.MobileNo
            tvUsername.text = objVerifyDtls.firstName
        }
        closeImage.setOnClickListener {
          closeDrawer()
       }
    }

    private fun openDrawer(){
        drawerLayout.openDrawer(GravityCompat.START)
    }
    private fun closeDrawer(){
        drawerLayout.closeDrawer(GravityCompat.START)
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

    override fun onMenuClick() {
            openDrawer()
    }

    override fun onNotificationClick() {
    }

    override fun onLoanClick() {
    }

    override fun onRealEstateClick() {
    }
}