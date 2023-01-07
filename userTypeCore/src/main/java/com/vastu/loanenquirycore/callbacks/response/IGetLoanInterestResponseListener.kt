package com.vastu.loanenquirycore.callbacks.response

import com.vastu.loanenquirycore.model.response.interest.loan.LoanInterestMainResponse
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IGetLoanInterestResponseListener:NetworkFailedListener {
    fun getLoanInterestSuccessResponse(loanInterestMainResponse: LoanInterestMainResponse)
    fun getLoanInterestFailureResponse(loanInterestMainResponse: LoanInterestMainResponse)
}