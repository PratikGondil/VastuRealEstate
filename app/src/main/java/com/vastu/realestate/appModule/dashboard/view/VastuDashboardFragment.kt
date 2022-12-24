package com.vastu.realestate.appModule.dashboard.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.vastu.realestate.R
import com.vastu.realestate.appModule.activity.LoginActivity
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IDashboardViewListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.INavDrawerListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IPropertySliderListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.dashboard.viewmodel.RealEstateDetailsViewModel
import com.vastu.realestate.appModule.dashboard.viewmodel.VastuDashboardViewModel
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyDtls
import com.vastu.realestate.databinding.FragmentVastuDashboardBinding
import com.vastu.realestate.utils.PreferenceKEYS
import com.vastu.realestate.utils.PreferenceKEYS.DASHBOARD_SLIDER_LIST
import com.vastu.realestate.utils.PreferenceKEYS.IS_LOGIN
import com.vastu.realestate.utils.PreferenceManger
import com.vastu.realestate.utils.PreferenceManger.clearPreferences
import com.vastu.slidercore.model.response.GetPropertySliderImagesResponse
import com.vastu.slidercore.model.response.PropertySliderResponseMain
import com.vastu.usertypecore.model.response.ObjGetUserTypeResMain

class VastuDashboardFragment : BaseFragment(), IDashboardViewListener,IToolbarListener,
    INavDrawerListener, IPropertySliderListener {
        private lateinit var dashboardBinding: FragmentVastuDashboardBinding
        private lateinit var viewModel: VastuDashboardViewModel
        private lateinit var drawerViewModel: DrawerViewModel
        private lateinit var objVerifyDetails:ObjVerifyDtls
        private lateinit var realEstateDetailsViewModel: RealEstateDetailsViewModel
        private lateinit var getPropertySliderImagesResponse: GetPropertySliderImagesResponse
        private val imageList = ArrayList<SlideModel>()

        companion object{
            var userType:String? = null
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        realEstateDetailsViewModel = ViewModelProvider(this)[RealEstateDetailsViewModel::class.java]
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        viewModel = ViewModelProvider(this)[VastuDashboardViewModel::class.java]
        dashboardBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_vastu_dashboard, container, false)
        dashboardBinding.lifecycleOwner = this
        dashboardBinding.vastuDashboardViewModel = viewModel
        dashboardBinding.drawerViewModel= drawerViewModel
        viewModel.iDashboardViewListener = this
        dashboardBinding.drawerViewModel = drawerViewModel
        drawerViewModel.iToolbarListener = this
        drawerViewModel.iNavDrawerListener = this
        realEstateDetailsViewModel .iPropertySliderListener = this
        getUserDetails()
        getUserType()
        return dashboardBinding.root
    }

    private fun getUserDetails(){
        val isLogin = PreferenceManger.get<Boolean>(IS_LOGIN)
        if(isLogin!!){
            objVerifyDetails = PreferenceManger.get<ObjVerifyDtls>(PreferenceKEYS.USER)!!
            drawerViewModel.mobileNo.set(objVerifyDetails.mobileNo!!)
            drawerViewModel.userName.set(objVerifyDetails.firstName!!)
            DashboardActivity.userId = objVerifyDetails.userId!!
        }
        getPropertySlider()
    }

    private fun getPropertySlider(){
        showProgressDialog()
        realEstateDetailsViewModel.getPropertySlider("1")
    }
     private fun getUserType(){
        showProgressDialog()
        DashboardActivity.userId?.let { viewModel.getUserType(it) }
    }

    override fun onLoanClick() {
        findNavController().navigate(R.id.action_VastuDashboardFragment_to_LoanFragment)
    }

    override fun onSuccessGetUserType(objGetUserTypeResMain: ObjGetUserTypeResMain) {
       hideProgressDialog()
        userType = objGetUserTypeResMain.getUserTypeDataDetailsResponse.userTypeData.get(0).userType
    }

    override fun onFailGetUserType(objGetUserTypeResMain: ObjGetUserTypeResMain) {
        hideProgressDialog()
        Toast.makeText(requireContext(),objGetUserTypeResMain.userTypeResponse.responseStatusHeader.statusDescription,
            Toast.LENGTH_LONG).show()
    }

    override fun onRealEstateClick() {
        findNavController().navigate(R.id.action_VastuDashboardFragment_to_RealEstateFragment)
    }
    private fun openDrawer(){
        dashboardBinding.drawerLayout.openDrawer(GravityCompat.START)
    }
    private fun closeDrawer(){
        dashboardBinding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    override fun onResume() {
        super.onResume()
        drawerViewModel.toolbarTitle.set(getString(R.string.vastu))
        drawerViewModel.isDashBoard.set(true)
    }

    override fun onClickBack() {
       activity?.onBackPressed()
    }
    override fun onClickMenu() {
        openDrawer()
    }
    override fun onClickNotification() {
        //openNotificationFragment
    }
    override fun onClickClose() {
        closeDrawer()
    }
    override fun onClickEnquiry() {
        closeDrawer()
    }
    override fun onClickAddProperty() {
        closeDrawer()
    }
    override fun onClickContactUs() {
        closeDrawer()
    }
    override fun onClickSettings() {
        closeDrawer()
    }
    override fun onClickLogout() {
        clearPreferences()
        activity?.finishAffinity()
        startActivity(Intent(activity, LoginActivity::class.java))
    }

    override fun onSuccessPropertySliderById(propertySliderResponseMain: PropertySliderResponseMain) {
        hideProgressDialog()
        imageList.clear()
        getPropertySliderImagesResponse = propertySliderResponseMain.getPropertySliderImagesResponse
        for( slider in getPropertySliderImagesResponse.propertySliderImages!!){
            imageList.add(SlideModel(slider.image))
        }
        PreferenceManger.saveSlider(getPropertySliderImagesResponse, DASHBOARD_SLIDER_LIST)
        dashboardBinding.apply {
            imageSlider.setImageList(imageList, ScaleTypes.FIT)
            imageSlider.startSliding(3000)
        }
    }

    override fun onFailurePropertySliderById(propertySliderResponseMain: PropertySliderResponseMain) {
        hideProgressDialog()
    }
}

/*
else if(activityDashboardBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
    closeDrawer()
}*/
