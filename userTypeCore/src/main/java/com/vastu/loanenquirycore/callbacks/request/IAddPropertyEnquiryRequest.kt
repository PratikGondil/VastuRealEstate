package com.vastu.loanenquirycore.callbacks.request

import android.content.Context
import com.vastu.loanenquirycore.callbacks.response.IAddPropertyEnquiryResponseListener
import com.vastu.loanenquirycore.model.request.AddPropertyEnquiryRequest

interface IAddPropertyEnquiryRequest {
    fun callAddPropertyEnquiry(context: Context, urlEndPoint:String, addPropertyEnquiryRequest: AddPropertyEnquiryRequest, iAddPropertyEnquiryResponseListener: IAddPropertyEnquiryResponseListener)
}