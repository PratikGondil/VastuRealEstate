package com.vastu.realestate.appModule.dashboard.view

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.vastu.enquiry.property.model.response.EnquiryData
import com.vastu.realestate.R
import com.vastu.realestate.appModule.activity.LoginActivity
import com.vastu.realestate.appModule.dashboard.uiInterfaces.*
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.dashboard.viewmodel.VastuDashboardViewModel
import com.vastu.realestate.appModule.enquirylist.view.EnquiryActivity
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyDtls
import com.vastu.realestate.databinding.FragmentVastuDashboardBinding
import com.vastu.realestate.databinding.MainNavDrawerBinding
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestate.utils.BaseConstant.ADMIN
import com.vastu.realestate.utils.BaseConstant.BUILDER
import com.vastu.realestate.utils.BaseConstant.CUSTOMER
import com.vastu.realestate.utils.BaseConstant.EMPLOYEES
import com.vastu.realestate.utils.BaseConstant.IS_FROM_PROPERTY_LIST
import com.vastu.realestate.utils.PreferenceKEYS
import com.vastu.realestate.utils.PreferenceKEYS.DASHBOARD_SLIDER_LIST
import com.vastu.realestate.utils.PreferenceKEYS.IS_LOGIN
import com.vastu.realestate.utils.PreferenceManger
import com.vastu.realestate.utils.PreferenceManger.clearPreferences
import com.vastu.slidercore.model.response.advertisement.GetAdvertisementSliderMainResponse
import com.vastu.usertypecore.model.response.ObjGetUserTypeResMain

class VastuDashboardFragment : BaseFragment(), IDashboardViewListener,IToolbarListener,
    INavDrawerListener, IAdvertisementSliderListener {
        private lateinit var dashboardBinding: FragmentVastuDashboardBinding
        private lateinit var navBinding:MainNavDrawerBinding
        private lateinit var viewModel: VastuDashboardViewModel
        private lateinit var drawerViewModel: DrawerViewModel
        private lateinit var objVerifyDetails:ObjVerifyDtls
        private val imageList = ArrayList<SlideModel>()

        companion object{
            var userType:String? = null
            var userId: String? = null
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        viewModel = ViewModelProvider(this)[VastuDashboardViewModel::class.java]
        navBinding = DataBindingUtil.inflate(inflater,R.layout.main_nav_drawer,container,false)
        dashboardBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_vastu_dashboard, container, false)
        dashboardBinding.lifecycleOwner = this
        dashboardBinding.vastuDashboardViewModel = viewModel
        dashboardBinding.drawerViewModel= drawerViewModel

        viewModel.iDashboardViewListener = this

        drawerViewModel.iToolbarListener = this

        drawerViewModel.iNavDrawerListener = this

        viewModel.iAdvertisementSliderListener = this

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
            userId = objVerifyDetails.userId!!
        }
    }

     private fun getUserType(){
        showProgressDialog()
        userId?.let { viewModel.getUserType(it) }
    }
    private fun getAdvertisementSlider(){
        showProgressDialog()
        viewModel.getAdvertisementSlider()
    }

    override fun onLoanClick() {
        findNavController().navigate(R.id.action_VastuDashboardFragment_to_LoanFragment)
    }

    override fun onSuccessGetUserType(objGetUserTypeResMain: ObjGetUserTypeResMain) {
       hideProgressDialog()
        userType = objGetUserTypeResMain.getUserTypeDataDetailsResponse.userTypeData.get(0).userType
        layoutChange()
        getAdvertisementSlider()
    }

    override fun onFailGetUserType(objGetUserTypeResMain: ObjGetUserTypeResMain) {
        hideProgressDialog()
        Toast.makeText(requireContext(),objGetUserTypeResMain.userTypeResponse.responseStatusHeader.statusDescription,
            Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        drawerViewModel.toolbarTitle.set(getString(R.string.vastu))
        drawerViewModel.isDashBoard.set(true)
    }
    private fun layoutChange(){
        navBinding.apply {
            when(userType){
                ADMIN->{
                    navEnquiry.visibility = View.VISIBLE
                    navProperties.visibility = View.VISIBLE
                    dashboardBinding.floatAddProperty.visibility = View.VISIBLE
                }
                CUSTOMER->{
                    navEnquiry.visibility = View.GONE
                    navProperties.visibility = View.GONE
                    dashboardBinding.floatAddProperty.visibility = View.GONE
                }
                BUILDER->{
                    navEnquiry.visibility = View.GONE
                    navProperties.visibility = View.VISIBLE
                    dashboardBinding.floatAddProperty.visibility = View.VISIBLE
                }
                EMPLOYEES->{

                }
            }
        }
    }

    override fun onSuccessAdvertisementSlider(advertisementSliderMainResponse: GetAdvertisementSliderMainResponse) {
        hideProgressDialog()
        imageList.clear()
        for( slider in advertisementSliderMainResponse.getAdvertiseDetailsResponse.advertiseData){
            imageList.add(SlideModel(slider.adSlider))
        }
        PreferenceManger.saveAdvertisementSlider(advertisementSliderMainResponse.getAdvertiseDetailsResponse,
            DASHBOARD_SLIDER_LIST)
        dashboardBinding.apply {
            imageSlider.setImageList(imageList, ScaleTypes.FIT)
            imageSlider.startSliding(3000)
        }
    }

    override fun onFailureAdvertisementSlider(advertisementSliderMainResponse: GetAdvertisementSliderMainResponse) {
        hideProgressDialog()
    }

    override fun onUserNotConnected() {
        hideProgressDialog()
        showDialog("",false,true)
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
        startActivity(Intent(activity, EnquiryActivity::class.java))
        closeDrawer()
    }

    override fun onClickProperties() {
        //open PropertyList
        closeDrawer()
    }

    override fun onClickAddProperty() {
        val bundle = Bundle()
        bundle.putSerializable(IS_FROM_PROPERTY_LIST, false)
        findNavController().navigate(R.id.action_VastuDashboardFragment_to_addPropertyFragment,bundle)
        closeDrawer()
    }

    override fun onClickOffers() {
       findNavController().navigate(R.id.action_VastuDashboardFragment_to_OfferFragment)
       closeDrawer()
    }

    override fun onClickContactUs() {
        closeDrawer()
    }
    override fun onClickSettings() {
        closeDrawer()
    }

    override fun onClickLogout() {
        closeDrawer()
        showLogoutDialog()
    }

    private fun showLogoutDialog(){
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.log_out_dialog)
        dialog.findViewById<TextView>(R.id.tvYes).setOnClickListener {
            logOut()
        }
        dialog.findViewById<TextView>(R.id.tvNo).setOnClickListener {
            dialog.hide()
        }
        dialog.show()
    }
    private fun logOut(){
        clearPreferences()
        activity?.finishAffinity()
        startActivity(Intent(activity, LoginActivity::class.java))
    }
}
