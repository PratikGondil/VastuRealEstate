package com.vastu.loanenquirycore.callbacks.response

import com.vastu.loanenquirycore.model.response.enquiry.EnquiryMainResponse
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IAddLoanEnquiryResponseListener:NetworkFailedListener {
    fun onAddLoanEnquirySuccessResponse(enquiryMainResponse: EnquiryMainResponse)
    fun onAddLoanEnquiryFailureResponse(enquiryMainResponse: EnquiryMainResponse)
}