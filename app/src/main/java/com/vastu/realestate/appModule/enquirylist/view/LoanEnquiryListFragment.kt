package com.vastu.realestate.appModule.enquirylist.view

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vastu.enquiry.loan.model.response.GetLoanEnquiryListMainResponse
import com.vastu.enquiry.loan.model.response.LoanData
import com.vastu.enquiry.property.model.response.EnquiryData
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.view.filter.SortAndFilterScreen
import com.vastu.realestate.appModule.enquirylist.adapter.LoanEnquiryAdapter
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.IAssignLeadListener
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.ILoanListListener
import com.vastu.realestate.appModule.enquirylist.viewmodel.LoanEnquiryViewModel
import com.vastu.realestate.databinding.FragmentLoanEnquiryListBinding
import com.vastu.realestate.utils.BaseConstant

class LoanEnquiryListFragment : BaseFragment(), ILoanListListener, IAssignLeadListener,
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var loanEnquiryViewModel: LoanEnquiryViewModel
    private lateinit var loanEnquiryBinding:FragmentLoanEnquiryListBinding

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
        getLoanEnquiry()
        return loanEnquiryBinding.root
    }

    private fun getLoanEnquiry(){
        loanEnquiryBinding.loadingLayout.startShimmerAnimation()
        loanEnquiryViewModel.callGetLoanEnquiry()
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
        val loanAdapter = LoanEnquiryAdapter(loanList,this)
        loanRecyclerview.layoutManager = LinearLayoutManager(activity)
        loanRecyclerview.adapter = loanAdapter
    }

    override fun onFailureGetLoanEnquiry(getLoanEnquiryListMainResponse: GetLoanEnquiryListMainResponse) {
        stopShimmerAnimation()
        showDialog(getLoanEnquiryListMainResponse.loanDataResponse.responseStatusHeader.statusDescription!!,false,false)
    }

    override fun onUserNotConnected() {
        stopShimmerAnimation()
        showDialog("",false,true)
    }
    override fun onRefresh() {
       loanEnquiryBinding.swipeContainer.isRefreshing = false
       getLoanEnquiry()
    }

    override fun assignLoanLeadToEmployee(loanData: LoanData) {
        try{
            val modalbottomSheetFragment = AssignLeadsFragment(this)
            modalbottomSheetFragment.setStyle(
                BottomSheetDialogFragment.STYLE_NORMAL,android.R.style.Theme_Translucent_NoTitleBar
            )
            val bundle = Bundle()
            modalbottomSheetFragment.arguments = bundleOf(BaseConstant.LOAN_DATA to loanData)
            modalbottomSheetFragment.show(requireActivity().supportFragmentManager,modalbottomSheetFragment.tag)
        }catch (e : Exception){
            e.printStackTrace()
        }
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