package com.vastu.loanenquirycore.callbacks.response

import com.vastu.loanenquirycore.model.response.enquiry.EnquiryMainResponse
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IAddPropertyEnquiryResponseListener:NetworkFailedListener {
    fun onAddPropertyEnquirySuccessResponse(enquiryMainResponse: EnquiryMainResponse)
    fun onAddPropertyEnquiryFailureResponse(enquiryMainResponse: EnquiryMainResponse)
}