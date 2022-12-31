package com.vastu.loanenquirycore.callbacks.request

import com.vastu.loanenquirycore.callbacks.response.IGetLoanInterestResponseListener

interface IGetLoanInterestRequest {
    fun callGetLoanInterest(urlEndPoint:String, iGetLoanInterestResponseListener: IGetLoanInterestResponseListener)
}