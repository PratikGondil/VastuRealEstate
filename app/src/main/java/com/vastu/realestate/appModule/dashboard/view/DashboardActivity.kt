package com.vastu.realestate.appModule.dashboard.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.viewmodel.DashboardViewModel
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyDtls
import com.vastu.realestate.databinding.ActivityVastuDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var objVerifyDtls:ObjVerifyDtls
    private lateinit var dashboardViewModel: DashboardViewModel;
    private lateinit var activityVastuDashboardBinding: ActivityVastuDashboardBinding
    private lateinit var tvUsername:TextView
    private lateinit var tvMobile:TextView
    private lateinit var closeImage:ImageView
    private lateinit var toolbarLayout :LinearLayout
    private lateinit var menuImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var notificationImageView: ImageView
    private val imageList = ArrayList<SlideModel>()

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
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        activityVastuDashboardBinding = DataBindingUtil.setContentView(this,R.layout.activity_vastu_dashboard)
        activityVastuDashboardBinding.lifecycleOwner = this
        activityVastuDashboardBinding.dashboardViewModel = dashboardViewModel
    }

    override fun onStart() {
        super.onStart()
        initNavigationMenus()
    }
    private fun initNavigationMenus(){
        titleTextView = findViewById(R.id.title_textview)
        tvUsername = findViewById(R.id.username_textview)
        tvMobile = findViewById(R.id.mobile_textview)
        menuImageView = findViewById(R.id.menu_imageview)
        notificationImageView = findViewById(R.id.notification_imageview)
        closeImage = findViewById(R.id.close_imageview)
        objVerifyDtls = intent.getSerializableExtra(USER_DETAILS) as ObjVerifyDtls;

        activityVastuDashboardBinding.apply {

            if(objVerifyDtls!=null) {

                tvUsername.text = objVerifyDtls.firstName
                tvMobile.text = objVerifyDtls.mobileNo

                mainNavView.setNavigationItemSelectedListener {  menuItem ->onNavigationItemSelected(menuItem) }
            }
            menuImageView.setOnClickListener {
                openDrawer()
            }
            closeImage.setOnClickListener {
                closeDrawer()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setTitle(getString(R.string.vastu))
        initSlider()
    }

    private fun setTitle(title:String?){
        titleTextView.text = title
    }

    private fun initSlider(){
        activityVastuDashboardBinding.apply {
            imageList.add(SlideModel(R.drawable.banner))
            imageList.add(SlideModel(R.drawable.banner))
            imageList.add(SlideModel(R.drawable.banner))
            imageSlider.setImageList(imageList, ScaleTypes.FIT)
            imageSlider.startSliding(3000)
        }
    }

    fun onNavigationItemSelected(item: MenuItem): Boolean {
        activityVastuDashboardBinding.apply {
            when (item.itemId) {
                R.id.nav_enquiry -> {
                    closeDrawer()
                }
                R.id.nav_add_property -> {
                    closeDrawer()
                }
                R.id.nav_contact_us -> {
                    closeDrawer()
                }
                R.id.nav_settings -> {
                    closeDrawer()
                }
                R.id.nav_log_out -> {
                    closeDrawer()
                }
            }
            activityVastuDashboardBinding.drawerLayout.closeDrawer(GravityCompat.START)
            return true
        }
    }

    private fun openDrawer(){
        activityVastuDashboardBinding.drawerLayout.openDrawer(GravityCompat.START)
    }
    private fun closeDrawer(){
        activityVastuDashboardBinding.drawerLayout.closeDrawer(GravityCompat.START)
    }
    override fun onBackPressed() {
        val navHostFragment: NavHostFragment? =
            supportFragmentManager.findFragmentById(R.id.dashboardNavHost) as NavHostFragment?
        val fragment = navHostFragment!!.childFragmentManager.fragments[0]
        if (fragment is LoanFragment || fragment is RealEstateDetailsFragment) {
            super.onBackPressed()
        }else if(activityVastuDashboardBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
           closeDrawer()
        }else if( fragment is RealEstateFragment)
        {
            fragment.onBackClick()
        }
        else {
            finishAffinity()
        }
    }
}