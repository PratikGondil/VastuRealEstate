package com.vastu.realestate.appModule.dashboard.view

import android.os.Bundle
import android.widget.Toast

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IAdvertisementSliderListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IDashboardViewListener
import com.vastu.realestate.appModule.dashboard.view.offers.OffersFragment
import com.vastu.realestate.appModule.dashboard.viewmodel.VastuDashboardViewModel
import com.vastu.realestate.appModule.enquiry.view.AddLoanEnquiryFragment
import com.vastu.realestate.appModule.enquiry.view.AddPropertyEnquiryFragment
import com.vastu.realestate.appModule.properties.view.PropertiesFragment
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyDtls
import com.vastu.realestate.databinding.ActivityVastuDashboardBinding
import com.vastu.realestate.utils.PreferenceKEYS
import com.vastu.realestate.utils.PreferenceManger
import com.vastu.slidercore.model.request.MainPagerSliderRequest
import com.vastu.slidercore.model.response.advertisement.GetAdvertisementSliderMainResponse
import com.vastu.slidercore.model.response.mainpage.MainPageSliderResponse
import com.vastu.usertypecore.model.response.ObjGetUserTypeResMain

class DashboardActivity : BaseActivity(),  IAdvertisementSliderListener {
    private lateinit var viewModel: VastuDashboardViewModel
    private lateinit var activityDashboardBinding: ActivityVastuDashboardBinding
    private var mainPagerSliderRequest= MainPagerSliderRequest()

    companion object{
        var userType:String? = null
        var userId: String? = null
        var areaId:String?=null
        var objVerifyDetails = ObjVerifyDtls()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[VastuDashboardViewModel::class.java]
        activityDashboardBinding = DataBindingUtil.setContentView(this,R.layout.activity_vastu_dashboard)
        activityDashboardBinding.lifecycleOwner = this
        viewModel.iAdvertisementSliderListener = this
        getUserDetails()
        getUserType()
    }

    private fun getUserDetails(){
        val isLogin = PreferenceManger.get<Boolean>(PreferenceKEYS.IS_LOGIN)
        if(isLogin!!){
            objVerifyDetails = PreferenceManger.get<ObjVerifyDtls>(PreferenceKEYS.USER)!!
            userId = objVerifyDetails.userId!!
            areaId = objVerifyDetails.city
        }
    }

    private fun getUserType(){
        showProgressDialog()
        userId?.let { viewModel.getUserType(it) }
    }

    override fun onSuccessGetUserType(objGetUserTypeResMain: ObjGetUserTypeResMain) {
        hideProgressDialog()
        userType = objGetUserTypeResMain.getUserTypeDataDetailsResponse.userTypeData.get(0).userType
        getAdvertisementSlider()
    }
    private fun getAdvertisementSlider(){
        showProgressDialog()
        viewModel.getAdvertisementSlider()
    }

    override fun onFailGetUserType(objGetUserTypeResMain: ObjGetUserTypeResMain) {
        hideProgressDialog()
        showDialog(objGetUserTypeResMain.userTypeResponse.responseStatusHeader.statusDescription, isSuccess = false, isNetworkFailure = false)
    }

    override fun onSuccessMainSlider(mainPageSliderResponse: MainPageSliderResponse) {
        hideProgressDialog()
        PreferenceManger.saveSlider(mainPageSliderResponse.getMainSliderDetailsResponse,
            PreferenceKEYS.MAIN_SLIDER_LIST
        )
        getMainSlider()
    }

    override fun onFailureAdvertisementSlider(advertisementSliderMainResponse: GetAdvertisementSliderMainResponse) {
        hideProgressDialog()
        showDialog(advertisementSliderMainResponse.advertiseResponse.responseStatusHeader.statusDescription, isSuccess = false, isNetworkFailure = false)
    }

    private fun getMainSlider(){
        showProgressDialog()
        mainPagerSliderRequest = mainPagerSliderRequest.copy(areaId = areaId,
            userId= userId)
        viewModel.getMainSlider(mainPagerSliderRequest)
    }

    override fun onSuccessAdvertisementSlider(advertisementSliderMainResponse: GetAdvertisementSliderMainResponse) {
        hideProgressDialog()
        PreferenceManger.saveAdvertisementSlider(advertisementSliderMainResponse.getAdvertiseDetailsResponse,
            PreferenceKEYS.DASHBOARD_SLIDER_LIST
        )
    }

    override fun onFailureMainSlider(mainPageSliderResponse: MainPageSliderResponse) {
       hideProgressDialog()
        showDialog(mainPageSliderResponse.mainSliderResponse.responseStatusHeader.statusDescription, isSuccess = false, isNetworkFailure = false)
    }

    override fun onUserNotConnected() {
        hideProgressDialog()
        showDialog("", isSuccess = false, isNetworkFailure = true)
    }

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.dashboardNavHost) as NavHostFragment?
        val fragment = navHostFragment!!.childFragmentManager.fragments[0]
        if (fragment is LoanFragment || fragment is RealEstateFragment || fragment is RealEstateDetailsFragment
            || fragment is AddLoanEnquiryFragment || fragment is AddPropertyEnquiryFragment
            || fragment is AddPropertyFragment || fragment is OffersFragment
            ||fragment is PropertiesFragment) {
            super.onBackPressed()
        }else {
            finishAffinity()
        }
    }
}