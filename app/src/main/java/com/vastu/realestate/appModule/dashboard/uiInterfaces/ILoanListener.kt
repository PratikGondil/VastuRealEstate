package com.vastu.realestate.appModule.dashboard.uiInterfaces

import com.vastu.loanenquirycore.model.response.interest.loan.LoanInterestMainResponse
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.INetworkFailListener

interface ILoanListener : INetworkFailListener {
    fun onClickPersonalLoan()
    fun onClickHomeLoan()
    fun onClickCarLoan()
    fun fabAddLoanEnquiry()
    fun onLoanInterestedInListSuccess(loanInterestMainResponse: LoanInterestMainResponse)
    fun onLoanInterestedInListFailure(loanInterestMainResponse: LoanInterestMainResponse)
}