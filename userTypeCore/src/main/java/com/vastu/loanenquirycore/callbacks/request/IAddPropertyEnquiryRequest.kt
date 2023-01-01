package com.vastu.loanenquirycore.callbacks.request

import com.vastu.loanenquirycore.callbacks.response.IAddPropertyEnquiryResponseListener
import com.vastu.loanenquirycore.model.request.AddPropertyEnquiryRequest

interface IAddPropertyEnquiryRequest {
    fun callAddPropertyEnquiry(urlEndPoint:String,addPropertyEnquiryRequest: AddPropertyEnquiryRequest,iAddPropertyEnquiryResponseListener: IAddPropertyEnquiryResponseListener)
}