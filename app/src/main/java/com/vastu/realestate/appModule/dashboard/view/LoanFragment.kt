package com.vastu.realestate.appModule.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.vastu.loanenquirycore.model.response.enquiry.EnquiryMainResponse
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.ILoanListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.dashboard.viewmodel.LoanViewModel
import com.vastu.realestate.commoncore.model.otp.ObjUserData
import com.vastu.realestate.databinding.FragmentLoanBinding
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestate.utils.PreferenceKEYS
import com.vastu.realestate.utils.PreferenceManger
import com.vastu.slidercore.model.response.advertisement.GetAdvertiseDetailsResponse
import com.vastu.slidercore.model.response.property.GetPropertySliderImagesResponse

class LoanFragment : BaseFragment(),IToolbarListener,ILoanListener {

    private lateinit var loanBinding: FragmentLoanBinding
    private lateinit var loanViewModel: LoanViewModel
    private lateinit var drawerViewModel: DrawerViewModel
    private lateinit var getAdvertisementSlider: GetAdvertiseDetailsResponse
    private val imageList = ArrayList<SlideModel>()
    private lateinit var enquiryMainResponse: EnquiryMainResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loanViewModel = ViewModelProvider(this)[LoanViewModel::class.java]
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        loanBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_loan, container, false)
        loanBinding.lifecycleOwner = this
        drawerViewModel.iToolbarListener = this
        loanViewModel.iLoanListener = this
        loanBinding.loanViewModel = loanViewModel
        loanBinding.drawerViewModel= drawerViewModel
        setSliderData()
        return loanBinding.root
    }

    private fun setSliderData(){
        imageList.clear()
        getAdvertisementSlider = PreferenceManger.getAdvertisementSlider(PreferenceKEYS.DASHBOARD_SLIDER_LIST)
        loanBinding.apply {
            for( slider in getAdvertisementSlider.advertiseData){
                imageList.add(SlideModel(slider.adSlider))
            }
            imageSlider.setImageList(imageList, ScaleTypes.FIT)
            imageSlider.startSliding(3000)
        }
    }

    override fun onResume() {
        super.onResume()
        drawerViewModel.toolbarTitle.set(getString(R.string.loan))
        drawerViewModel.isDashBoard.set(false)
        //getLoanData()
    }

    private fun getLoanData() {
        val args = arguments
        if (args != null) {
            if (args.getSerializable(BaseConstant.ENQUIRY_RESPONSE) != null) {
                enquiryMainResponse = args.getSerializable(BaseConstant.ENQUIRY_RESPONSE) as EnquiryMainResponse
                val status = args.getBoolean(BaseConstant.STATUS,false)
                showDialog(enquiryMainResponse.registerResponse.responseStatusHeader.statusDescription,status,false)
            }
        }
    }

    override fun onClickBack() {
        activity?.onBackPressed()
    }

    override fun onClickMenu() {
        //onClickMenu
    }

    override fun onClickNotification() {
        //onClickNotification
    }

    override fun onClickPersonalLoan() {
       //onClickPersonalLoan
    }

    override fun onClickHomeLoan() {
        //onClickHomeLoan
    }

    override fun onClickCarLoan() {
        //onClickCarLoan
    }

    override fun fabAddLoanEnquiry() {
        findNavController().navigate(R.id.action_LoanFragment_to_AddLoanEnquiryFragment)
    }
}