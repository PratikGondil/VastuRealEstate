package com.vastu.realestate.appModule.dashboard.view

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.vastu.realestate.R
import com.vastu.realestate.appModule.activity.LoginActivity
import com.vastu.realestate.appModule.dashboard.uiInterfaces.*
import com.vastu.realestate.appModule.dashboard.view.DashboardActivity.Companion.userType
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.dashboard.viewmodel.VastuDashboardViewModel
import com.vastu.realestate.appModule.enquirylist.view.EnquiryActivity
import com.vastu.realestate.databinding.FragmentVastuDashboardBinding
import com.vastu.realestate.utils.BaseConstant.ADMIN
import com.vastu.realestate.utils.BaseConstant.BUILDER
import com.vastu.realestate.utils.BaseConstant.CUSTOMER
import com.vastu.realestate.utils.BaseConstant.EMPLOYEES
import com.vastu.realestate.utils.BaseConstant.IS_FROM_PROPERTY_LIST
import com.vastu.realestate.utils.PreferenceKEYS
import com.vastu.realestate.utils.PreferenceManger
import com.vastu.realestate.utils.PreferenceManger.clearPreferences
import com.vastu.slidercore.model.response.advertisement.GetAdvertiseDetailsResponse
import com.vastu.slidercore.model.response.mainpage.GetMainSliderDetailsResponse

class DashboardFragment : BaseFragment(), IDashboardViewListener,IToolbarListener,
    INavDrawerListener {
        private lateinit var dashboardBinding: FragmentVastuDashboardBinding
        private lateinit var viewModel: VastuDashboardViewModel
        private lateinit var drawerViewModel: DrawerViewModel
        private val imageList = ArrayList<SlideModel>()
        private lateinit var getAdvertisementSlider: GetAdvertiseDetailsResponse
        private lateinit var getMainSliderDetailsResponse: GetMainSliderDetailsResponse


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutChange()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        viewModel = ViewModelProvider(this)[VastuDashboardViewModel::class.java]
        dashboardBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_vastu_dashboard, container, false)
        dashboardBinding.lifecycleOwner = this
        dashboardBinding.vastuDashboardViewModel = viewModel
        dashboardBinding.drawerViewModel= drawerViewModel

        viewModel.iDashboardViewListener = this

        drawerViewModel.iToolbarListener = this

        drawerViewModel.iNavDrawerListener = this

        setUserDetails()

        return dashboardBinding.root
    }
    private fun setUserDetails(){
        drawerViewModel.apply {
          mobileNo.set(DashboardActivity.objVerifyDetails.mobileNo!!)
          userName.set(DashboardActivity.objVerifyDetails.firstName!!)
        }
        Handler(Looper.getMainLooper()).postDelayed({setSliderData() }, 1000)
        //Handler(Looper.getMainLooper()).postDelayed({ setMainSliderData() }, 1000)
    }

    private fun setSliderData(){
        imageList.clear()
        getAdvertisementSlider = PreferenceManger.getAdvertisementSlider(PreferenceKEYS.DASHBOARD_SLIDER_LIST)
        if(getAdvertisementSlider.advertiseData.isNotEmpty()) {
            dashboardBinding.apply {
                for (slider in getAdvertisementSlider.advertiseData) {
                    imageList.add(SlideModel(slider.adSlider))
                }
                imageSlider.setImageList(imageList, ScaleTypes.FIT)
                imageSlider.startSliding(3000)
            }
        }
    }

    private fun setMainSliderData(){
        imageList.clear()
        getMainSliderDetailsResponse = PreferenceManger.getSlider(PreferenceKEYS.MAIN_SLIDER_LIST)
        if(getMainSliderDetailsResponse.propertyData.isNotEmpty()) {
            getMainSliderDetailsResponse = PreferenceManger.getSlider(PreferenceKEYS.MAIN_SLIDER_LIST)
            dashboardBinding.apply {
                for( slider in getMainSliderDetailsResponse.propertyData){
                    imageList.add(SlideModel(slider.propertyImage))
                }
                imageSlider.setImageList(imageList, ScaleTypes.FIT)
                imageSlider.startSliding(3000)
            }
        }

    }
    override fun onLoanClick() {
        showProgressDialog()
        findNavController().navigate(R.id.action_VastuDashboardFragment_to_LoanFragment)
    }

    override fun onResume() {
        super.onResume()
        drawerViewModel.toolbarTitle.set(getString(R.string.vastu))
        drawerViewModel.isDashBoard.set(true)
    }
    private fun layoutChange(){
            when(userType){
                ADMIN->{
                    viewModel.enquiry.set(View.VISIBLE)
                    viewModel.properties.set(View.VISIBLE)
                }
                BUILDER->{
                    viewModel.enquiry.set(View.GONE)
                    viewModel.properties.set(View.VISIBLE)
                }
                EMPLOYEES->{
                    viewModel.enquiry.set(View.GONE)
                    viewModel.properties.set(View.VISIBLE)
                }
                CUSTOMER->{
                    viewModel.enquiry.set(View.GONE)
                    viewModel.properties.set(View.GONE)
                    dashboardBinding.floatAddProperty.visibility = View.GONE
                }
            }
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
        findNavController().navigate(R.id.action_DashBoardFragment_to_PropertiesFragment)
        closeDrawer()
    }
   override fun onEmployessClick(){
       findNavController().navigate(R.id.action_VastuDashboardFragment_to_EmployeeListFragment)
       closeDrawer()


   }    override fun onClickAddProperty() {
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
