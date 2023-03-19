package com.vastu.realestate.appModule.enquirylist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vastu.enquiry.getAssignedEnquiry.request.ObjGetAssignedEnquiryReq
import com.vastu.enquiry.loan.model.response.GetLoanEnquiryListMainResponse
import com.vastu.enquiry.loan.model.response.LoanData
import com.vastu.enquiry.loan.model.response.getAssignedLoanEnquiry.ObjEmpEnquiryDetailsData
import com.vastu.enquiry.loan.model.response.getAssignedLoanEnquiry.ObjEmpEnquiryDetailsResMain
import com.vastu.enquiry.property.model.response.EnquiryData
import com.vastu.enquiry.property.model.response.getAssignedPropertyEnquiry.ObjEmpPropertyEnquiryDtlsData
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.view.DashboardFragment.Companion.userType
import com.vastu.realestate.appModule.enquirylist.adapter.AssignedLoanEnquiryAdapter
import com.vastu.realestate.appModule.enquirylist.adapter.LoanEnquiryAdapter
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.IAssignLeadListener
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.ILoanListListener
import com.vastu.realestate.appModule.enquirylist.viewmodel.LoanEnquiryViewModel
import com.vastu.realestate.commoncore.model.otp.response.ObjVerifyDtls
import com.vastu.realestate.databinding.FragmentLoanEnquiryListBinding
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestate.utils.PreferenceKEYS
import com.vastu.realestate.utils.PreferenceManger

class LoanEnquiryListFragment : BaseFragment(), ILoanListListener, IAssignLeadListener,
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var loanEnquiryViewModel: LoanEnquiryViewModel
    private lateinit var loanEnquiryBinding:FragmentLoanEnquiryListBinding
    var objVerifyDetails = ObjVerifyDtls()
    var objGetAssignedEnquiryReq = ObjGetAssignedEnquiryReq()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loanEnquiryViewModel = ViewModelProvider(this)[LoanEnquiryViewModel::class.java]
        loanEnquiryBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_loan_enquiry_list, container, false)
        loanEnquiryBinding.lifecycleOwner = this
        loanEnquiryViewModel.iLoanListListener = this
        loanEnquiryBinding.loanEnquiryViewModel = loanEnquiryViewModel
        loanEnquiryBinding.swipeContainer.setOnRefreshListener(this)
        loanEnquiryBinding.swipeContainer.setColorSchemeResources(R.color.button_color)
        if(userType.equals(BaseConstant.ADMIN))
        getLoanEnquiry()
        else if (userType.equals(BaseConstant.EMPLOYEES))
            getAssignedLoanEnquiry()
        return loanEnquiryBinding.root
    }

    private fun getLoanEnquiry(){
        loanEnquiryBinding.loadingLayout.startShimmerAnimation()
        loanEnquiryViewModel.callGetLoanEnquiry()
    }

    fun getAssignedLoanEnquiry(){
        loanEnquiryBinding.loadingLayout.startShimmerAnimation()
        objVerifyDetails = PreferenceManger.get<ObjVerifyDtls>(PreferenceKEYS.USER)!!
        objGetAssignedEnquiryReq = objGetAssignedEnquiryReq.copy(empId = objVerifyDetails.userId)
        loanEnquiryViewModel.callGetAssigndLoanEnq(objGetAssignedEnquiryReq)
    }
    override fun onSuccessGetLoanEnquiry(getLoanEnquiryListMainResponse: GetLoanEnquiryListMainResponse) {
        val loanList = getLoanEnquiryListMainResponse.getloanDetailsResponse.loanData
        if(loanList.isNotEmpty()) {
            stopShimmerAnimation()
            loanEnquiryBinding.rvLoanList.visibility = View.VISIBLE
            getLoanList(loanList)
        }else{
            stopShimmerAnimation()
            loanEnquiryBinding.rvLoanList.visibility = View.GONE
        }
    }
    private fun stopShimmerAnimation(){
        loanEnquiryBinding.loadingLayout.stopShimmerAnimation()
        loanEnquiryBinding.loadingLayout.visibility = View.GONE
    }

    private fun getLoanList(loanList:List<LoanData>) {
        val loanRecyclerview = loanEnquiryBinding.rvLoanList
        val loanAdapter = LoanEnquiryAdapter(loanList,this,userType)
        loanRecyclerview.layoutManager = LinearLayoutManager(activity)
        loanRecyclerview.adapter = loanAdapter
    }

    override fun onFailureGetLoanEnquiry(getLoanEnquiryListMainResponse: GetLoanEnquiryListMainResponse) {
        stopShimmerAnimation()
        showDialog(getLoanEnquiryListMainResponse.loanDataResponse.responseStatusHeader.statusDescription!!,false,false)
    }

    override fun onGetAssignedLoanLeadSuccess(objEmpEnquiryDetailsResMain: ObjEmpEnquiryDetailsResMain) {
        stopShimmerAnimation()
        loanEnquiryBinding.rvLoanList.visibility = View.VISIBLE

        var response = objEmpEnquiryDetailsResMain.getEmployeeEnquiryDetailsResponse?.objEmpEnquiryDetailsData
        val loanRecyclerview = loanEnquiryBinding.rvLoanList
        val loanAdapter = response?.let { AssignedLoanEnquiryAdapter(it,this,userType) }
        loanRecyclerview.layoutManager = LinearLayoutManager(activity)
        loanRecyclerview.adapter = loanAdapter
  }

    override fun onUserNotConnected() {
        stopShimmerAnimation()
        showDialog("",false,true)
    }
    override fun onRefresh() {
       loanEnquiryBinding.swipeContainer.isRefreshing = false
        if(userType.equals(BaseConstant.ADMIN))
            getLoanEnquiry()
        else if (userType.equals(BaseConstant.EMPLOYEES))
            getAssignedLoanEnquiry()
    }

    override fun assignLoanLeadToEmployee(loanData: LoanData) {
        try{
            val modalbottomSheetFragment = AssignLeadsFragment(this)
            modalbottomSheetFragment.setStyle(
                BottomSheetDialogFragment.STYLE_NORMAL,android.R.style.Theme_Translucent_NoTitleBar
            )
            val bundle = Bundle()
            modalbottomSheetFragment.arguments = bundleOf(BaseConstant.LOAN_DATA to loanData,BaseConstant.USER_TYPE to userType)
            modalbottomSheetFragment.show(requireActivity().supportFragmentManager,modalbottomSheetFragment.tag)
        }catch (e : Exception){
            e.printStackTrace()
        }
    }
    override fun updateLoanLeadStatus(loanData: ObjEmpEnquiryDetailsData) {
        try{
            val modalbottomSheetFragment = UpdateStatusFragment(this)
            modalbottomSheetFragment.setStyle(
                BottomSheetDialogFragment.STYLE_NORMAL,android.R.style.Theme_Translucent_NoTitleBar
            )
            val bundle = Bundle()
            modalbottomSheetFragment.arguments = bundleOf(BaseConstant.ASSIGNED_LOAN_DATA to loanData,BaseConstant.USER_TYPE to userType)
            modalbottomSheetFragment.show(requireActivity().supportFragmentManager,modalbottomSheetFragment.tag)
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    override fun updatePropertyLeadStatus(PropertyData: ObjEmpPropertyEnquiryDtlsData) {
    }

    override fun assignPropertyLeadToEmployee(PropertyData: EnquiryData) {
    }

    override fun onAssignLeadSuccess() {
       onRefresh()
    }

    override fun onEmpListFailure(message: String, isSuccess:Boolean, isNetworkFailure:Boolean)
    {
        showDialog(message,isSuccess,isNetworkFailure)
    }
}