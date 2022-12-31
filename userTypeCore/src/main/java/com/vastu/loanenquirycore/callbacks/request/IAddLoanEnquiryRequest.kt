package com.vastu.loanenquirycore.callbacks.request

import com.vastu.loanenquirycore.callbacks.response.IAddLoanEnquiryResponseListener
import com.vastu.loanenquirycore.callbacks.response.IGetLoanInterestResponseListener
import com.vastu.loanenquirycore.model.request.AddLoanEnquiryRequest

interface IAddLoanEnquiryRequest {
    fun callAddLoanEnquiry(urlEndPoint:String,addLoanEnquiryRequest: AddLoanEnquiryRequest,iAddLoanEnquiryResponseListener: IAddLoanEnquiryResponseListener)
}