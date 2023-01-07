package com.vastu.realestate.appModule.enquiry.uiinterfaces

import com.vastu.loanenquirycore.model.response.bank.BankResponseMain
import com.vastu.loanenquirycore.model.response.enquiry.EnquiryMainResponse
import com.vastu.loanenquirycore.model.response.interest.loan.LoanInterestMainResponse
import com.vastu.loanenquirycore.model.response.occupation.OccupationMainResponse
import com.vastu.realestate.appModule.enquirylist.uiinterfaces.INetworkFailListener

interface IAddLoanEnquiryListener :INetworkFailListener{
    fun onOccupationListFailure(occupationMainResponse: OccupationMainResponse)
    fun onLoanInterestedInLListFailure(loanInterestMainResponse: LoanInterestMainResponse)
    fun onBankListFailure(bankResponseMain: BankResponseMain)
    fun onClickAddLoanEnquiry()
    fun onAddLoanEnquiryFailure(enquiryMainResponse: EnquiryMainResponse)
    fun onGotoDashboard(enquiryMainResponse: EnquiryMainResponse)
}