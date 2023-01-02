package com.vastu.realestate.appModule.enquirylist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vastu.enquiry.loan.model.response.GetLoanEnquiryListMainResponse
import com.vastu.enquiry.loan.model.response.LoanData
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.enquirylist.adapter.LoanEnquiryAdapter
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.ILoanListListener
import com.vastu.realestate.appModule.enquirylist.viewmodel.LoanEnquiryViewModel
import com.vastu.realestate.databinding.FragmentLoanEnquiryListBinding

class LoanEnquiryListFragment : BaseFragment(), ILoanListListener{

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
        return loanEnquiryBinding.root
    }

    override fun onResume() {
        super.onResume()
        getLoanEnquiry()
    }

    private fun getLoanEnquiry(){
        showProgressDialog()
        loanEnquiryViewModel.callGetLoanEnquiry()
    }

    override fun onSuccessGetLoanEnquiry(getLoanEnquiryListMainResponse: GetLoanEnquiryListMainResponse) {
        hideProgressDialog()
        val loanList = getLoanEnquiryListMainResponse.getloanDetailsResponse.loanData
        getLoanList(loanList)
    }

    private fun getLoanList(loanList:List<LoanData>) {
        val loanRecyclerview = loanEnquiryBinding.rvLoanList
        val loanAdapter = LoanEnquiryAdapter(loanList)
        loanRecyclerview.layoutManager = LinearLayoutManager(activity)
        loanRecyclerview.adapter = loanAdapter
    }

    override fun onFailureGetLoanEnquiry(getLoanEnquiryListMainResponse: GetLoanEnquiryListMainResponse) {
        hideProgressDialog()
        showDialog(getLoanEnquiryListMainResponse.loanDataResponse.responseStatusHeader.statusDescription,false,false)
    }

    override fun onUserNotConnected() {
        hideProgressDialog()
        showDialog("",false,true)
    }

}