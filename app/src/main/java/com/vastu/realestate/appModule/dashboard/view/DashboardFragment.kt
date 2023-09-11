package com.vastu.realestate.appModule.dashboard.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.vastu.realestate.R
import com.vastu.realestate.appModule.activity.LoginActivity
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IAdvertisementSliderListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IDashboardViewListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.INavDrawerListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.dashboard.viewmodel.VastuDashboardViewModel
import com.vastu.realestate.appModule.enquirylist.view.EnquiryActivity
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyDtls
import com.vastu.realestate.databinding.FragmentVastuDashboardBinding
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestate.utils.BaseConstant.ADMIN
import com.vastu.realestate.utils.BaseConstant.BUILDER
import com.vastu.realestate.utils.BaseConstant.CUSTOMER
import com.vastu.realestate.utils.BaseConstant.EMPLOYEES
import com.vastu.realestate.utils.BaseConstant.IS_FROM_PROPERTY_LIST
import com.vastu.realestate.utils.PreferenceKEYS
import com.vastu.realestate.utils.PreferenceManger
import com.vastu.realestate.utils.PreferenceManger.clearPreferences
import com.vastu.slidercore.model.request.MainPagerSliderRequest
import com.vastu.slidercore.model.response.advertisement.AdvertiseData
import com.vastu.slidercore.model.response.advertisement.GetAdvertiseDetailsResponse
import com.vastu.slidercore.model.response.advertisement.GetAdvertisementSliderMainResponse
import com.vastu.slidercore.model.response.mainpage.GetMainSliderDetailsResponse
import com.vastu.slidercore.model.response.mainpage.MainPageSliderResponse
import com.vastu.usertypecore.model.response.ObjGetUserTypeResMain


class DashboardFragment : BaseFragment(), IDashboardViewListener, IToolbarListener,
    INavDrawerListener, IAdvertisementSliderListener {
    private lateinit var dashboardBinding: FragmentVastuDashboardBinding
    private var mainPagerSliderRequest = MainPagerSliderRequest()
    private lateinit var viewModel: VastuDashboardViewModel
    private lateinit var drawerViewModel: DrawerViewModel
    private val imageList = ArrayList<SlideModel>()
    private lateinit var getAdvertisementSlider: GetAdvertiseDetailsResponse
    private lateinit var getMainSliderDetailsResponse: GetMainSliderDetailsResponse


    companion object {
        var userType: String? = null
        var userId: String? = null
        var areaId: String? = null
        var objVerifyDetails = ObjVerifyDtls()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        viewModel = ViewModelProvider(this)[VastuDashboardViewModel::class.java]
        dashboardBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_vastu_dashboard, container, false)
        dashboardBinding.lifecycleOwner = this
        dashboardBinding.vastuDashboardViewModel = viewModel
        dashboardBinding.drawerViewModel = drawerViewModel
        viewModel.iAdvertisementSliderListener = this
        viewModel.iDashboardViewListener = this

        drawerViewModel.iToolbarListener = this

        drawerViewModel.iNavDrawerListener = this

        getUserDetails()

        return dashboardBinding.root


    }

    private fun getUserDetails() {
        val isLogin = PreferenceManger.get<Boolean>(PreferenceKEYS.IS_LOGIN)
        if (isLogin!!) {
            objVerifyDetails = PreferenceManger.get<ObjVerifyDtls>(PreferenceKEYS.USER)!!
            userId = objVerifyDetails.userId!!
            areaId = objVerifyDetails.city
        }
        setUserDetails()
        getUserType()
    }

    private fun getUserType() {
        showProgressDialog()
        userId?.let { viewModel.getUserType(it) }
    }

    override fun onSuccessGetUserType(objGetUserTypeResMain: ObjGetUserTypeResMain) {
        hideProgressDialog()
        userType = objGetUserTypeResMain.getUserTypeDataDetailsResponse.userTypeData.get(0).userType
        layoutChange()
        getAdvertisementSlider()
    }

    private fun getAdvertisementSlider() {
        showProgressDialog()
        viewModel.getAdvertisementSlider()
    }

    override fun onFailGetUserType(objGetUserTypeResMain: ObjGetUserTypeResMain) {
        hideProgressDialog()
        showDialog(
            objGetUserTypeResMain.userTypeResponse.responseStatusHeader.statusDescription!!,
            isSuccess = false,
            isNetworkFailure = false
        )
    }

    override fun onSuccessMainSlider(mainPageSliderResponse: MainPageSliderResponse) {
        hideProgressDialog()
        PreferenceManger.saveSlider(
            mainPageSliderResponse.getMainSliderDetailsResponse,
            PreferenceKEYS.MAIN_SLIDER_LIST
        )
        getMainSlider()
    }

    override fun onFailureAdvertisementSlider(advertisementSliderMainResponse: GetAdvertisementSliderMainResponse) {
        hideProgressDialog()
        showDialog(
            advertisementSliderMainResponse.advertiseResponse.responseStatusHeader.statusDescription!!,
            isSuccess = false,
            isNetworkFailure = false
        )
    }

    private fun getMainSlider() {
        showProgressDialog()
        mainPagerSliderRequest = mainPagerSliderRequest.copy(
            areaId = areaId,
            userId = userId
        )
        viewModel.getMainSlider(mainPagerSliderRequest)
    }

    override fun onSuccessAdvertisementSlider(advertisementSliderMainResponse: GetAdvertisementSliderMainResponse) {
        hideProgressDialog()
        PreferenceManger.saveAdvertisementSlider(
            advertisementSliderMainResponse.getAdvertiseDetailsResponse,
            PreferenceKEYS.DASHBOARD_SLIDER_LIST
        )
        setSliderData()
    }

    override fun onFailureMainSlider(mainPageSliderResponse: MainPageSliderResponse) {
        hideProgressDialog()
        showDialog(
            mainPageSliderResponse.mainSliderResponse.responseStatusHeader.statusDescription!!,
            isSuccess = false,
            isNetworkFailure = false
        )
    }

    override fun onUserNotConnected() {
        hideProgressDialog()
        showDialog("", isSuccess = false, isNetworkFailure = true)
    }

    private fun setUserDetails() {
        drawerViewModel.apply {
            mobileNo.set(objVerifyDetails.mobileNo!!)
            userName.set(objVerifyDetails.firstName!!)
        }
    }

    private fun setSliderData() {
        imageList.clear()
        dashboardBinding.imageSlider.visibility = View.VISIBLE
        getAdvertisementSlider =
            PreferenceManger.getAdvertisementSlider(PreferenceKEYS.DASHBOARD_SLIDER_LIST)
        if (getAdvertisementSlider.advertiseData.isNotEmpty()) {
            dashboardBinding.apply {
                for (slider in getAdvertisementSlider.advertiseData) {
                    imageList.add(SlideModel(slider.adSlider, ScaleTypes.FIT))
                }
                imageSlider.setImageList(imageList, ScaleTypes.FIT)
                imageSlider.startSliding(3000)
            }
        }

        dashboardBinding.imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                var selectedPostition = getAdvertisementSlider.advertiseData[position]
                if(selectedPostition.type =="video"){
                    createVideoDialog(getAdvertisementSlider.advertiseData[position])
                }else{
                    dashboardBinding.imageSlider.visibility = View.VISIBLE
                }

            }

        })
    }

    @SuppressLint("MissingInflatedId")
    private fun createVideoDialog(advertiseData: AdvertiseData) {
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.custom_video_dialog,null)
        val cross :ImageView = view.findViewById(R.id.img_cross)
        val videoView :VideoView=view.findViewById(R.id.andExoPlayerViewType)
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse(advertiseData.link))
        videoView.requestFocus();
        videoView.start()
        builder.setView(view)

        builder.setCanceledOnTouchOutside(false)
        builder.show()
        cross.setOnClickListener{
            builder.dismiss()
        }
    }

    private fun setMainSliderData() {
        imageList.clear()
        getMainSliderDetailsResponse = PreferenceManger.getSlider(PreferenceKEYS.MAIN_SLIDER_LIST)
        if (getMainSliderDetailsResponse.propertyData.isNotEmpty()) {
            getMainSliderDetailsResponse =
                PreferenceManger.getSlider(PreferenceKEYS.MAIN_SLIDER_LIST)
            dashboardBinding.apply {
                for (slider in getMainSliderDetailsResponse.propertyData) {
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

    private fun layoutChange() {
        when (userType) {
            ADMIN -> {
                drawerViewModel.employess.set(View.VISIBLE)
            }
            BUILDER -> {
                drawerViewModel.enquiry.set(View.GONE)
                drawerViewModel.offer.set(View.VISIBLE)
            }
            EMPLOYEES -> {
                drawerViewModel.offer.set(View.VISIBLE)
            }
            CUSTOMER -> {
                drawerViewModel.enquiry.set(View.GONE)
                drawerViewModel.properties.set(View.GONE)
                drawerViewModel.offer.set(View.VISIBLE)
                //  dashboardBinding.floatAddProperty.visibility = View.GONE
            }
        }
    }


    override fun onRealEstateClick() {
        findNavController().navigate(R.id.action_VastuDashboardFragment_to_RealEstateFragment)
    }

    private fun openDrawer() {
        dashboardBinding.drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun closeDrawer() {
        dashboardBinding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    override fun onClickBack() {
        activity?.onBackPressed()
    }

    override fun onClickMenu() {
        openDrawer()
    }

    override fun onClickNotification() {
        findNavController().navigate(R.id.action_vastuDashboardFragment_to_Notification)

    }

    override fun onClickClose() {
        closeDrawer()
    }

    override fun goToUserProfile() {
        if (userType.equals(EMPLOYEES) || userType.equals(CUSTOMER))
            findNavController().navigate(R.id.action_vastuDashboardFragment_to_EmployeeDetailsFragment)
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

    override fun onEmployessClick() {
        findNavController().navigate(R.id.action_VastuDashboardFragment_to_EmployeeListFragment)
        closeDrawer()


    }

    override fun onClickAddProperty() {
        val bundle = Bundle()
        bundle.putSerializable(IS_FROM_PROPERTY_LIST, false)
        findNavController().navigate(
            R.id.action_VastuDashboardFragment_to_addPropertyFragment,
            bundle
        )
        closeDrawer()
    }

    override fun onClickOffers() {
        findNavController().navigate(R.id.action_VastuDashboardFragment_to_OfferFragment)
        closeDrawer()
    }

    override fun onClickContactUs() {
        findNavController().navigate(R.id.action_vastuDashboardFragment_to_ContactUs)
        closeDrawer()
    }

    override fun onClickSettings() {
        closeDrawer()
    }

    override fun onClickLogout() {
        closeDrawer()
        showLogoutDialog()
    }

    override fun onRateUsClick() {
        findNavController().navigate(R.id.action_vastuDashboardFragment_to_RateUs)
        closeDrawer()
    }

    override fun onFeedbackClick() {
        findNavController().navigate(R.id.action_vastuDashboardFragment_to_feedback)
        closeDrawer()
    }

    override fun onLanguageClick() {
        findNavController().navigate(R.id.languageFragment)
        closeDrawer()
    }

    override fun onHomeClick() {
       closeDrawer()
    }

    private fun showLogoutDialog() {
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

    private fun logOut() {
        clearPreferences()
        activity?.finishAffinity()
        startActivity(Intent(activity, LoginActivity::class.java))
    }


}
