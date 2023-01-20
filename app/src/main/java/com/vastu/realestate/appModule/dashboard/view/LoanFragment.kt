package com.vastu.realestate.appModule.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.vastu.loanenquirycore.model.response.enquiry.EnquiryMainResponse
import com.vastu.loanenquirycore.model.response.interest.loan.LoanInterestMainResponse
import com.vastu.loanenquirycore.model.response.interest.loan.LoanInterstedData
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.adapter.LoanAdapter
import com.vastu.realestate.appModule.dashboard.adapter.OnItemClickListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.ILoanListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.dashboard.viewmodel.LoanViewModel
import com.vastu.realestate.databinding.FragmentLoanBinding
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestate.utils.BaseConstant.LOAN_DATA
import com.vastu.realestate.utils.PreferenceKEYS
import com.vastu.realestate.utils.PreferenceManger
import com.vastu.slidercore.model.response.advertisement.GetAdvertiseDetailsResponse

class LoanFragment : BaseFragment(),IToolbarListener,ILoanListener, OnItemClickListener {

    private lateinit var loanBinding: FragmentLoanBinding
    private lateinit var loanViewModel: LoanViewModel
    private lateinit var drawerViewModel: DrawerViewModel
    private lateinit var getAdvertisementSlider: GetAdvertiseDetailsResponse
    private val imageList = ArrayList<SlideModel>()
    private lateinit var enquiryMainResponse: EnquiryMainResponse
    private var loan = LoanInterstedData()

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
        getLoanList()
    }

    private fun getLoanList(){
        try {
           //showProgressDialog()
           loanViewModel.callLoanInterestedIn()
        } catch (e: Exception) {
            e.printStackTrace()
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

    override fun onLoanInterestedInListSuccess(loanInterestMainResponse: LoanInterestMainResponse) {
        hideProgressDialog()
        setLoanDetails(loanInterestMainResponse.getloanInterstedDetailsResponse.loanInterstedData)
    }
    private fun setLoanDetails(loanList:List<LoanInterstedData>){
        try {
            val recyclerViewLoan = loanBinding.loanRecyclerview
            val loanAdapter = LoanAdapter(this,loanList)
            recyclerViewLoan.adapter = loanAdapter
            recyclerViewLoan.layoutManager = LinearLayoutManager(activity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onLoanInterestedInListFailure(loanInterestMainResponse: LoanInterestMainResponse) {
        hideProgressDialog()
        showDialog(loanInterestMainResponse.loanInterstedResponse.responseStatusHeader.statusDescription,false,false)
    }

    override fun onUserNotConnected() {
        hideProgressDialog()
        showDialog("",false,true)
    }

    override fun onItemClick(loanData: LoanInterstedData) {
        loan = loanData
        if(loan.loanName!=null) {
            loanBinding.floatLoanEnquiry.visibility = View.VISIBLE
        }else{
            showDialog("Please Select Loan",isSuccess = false,isNetworkFailure = false)
        }
    }

    override fun fabAddLoanEnquiry() {
        val bundle = Bundle()
        bundle.putSerializable(LOAN_DATA, loan)
        findNavController().navigate(R.id.action_LoanFragment_to_AddLoanEnquiryFragment, bundle)
    }
}