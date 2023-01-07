package com.vastu.enquiry.loan.callback.response

import com.vastu.enquiry.loan.model.response.GetLoanEnquiryListMainResponse
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IGetLoanEnquiryListResponseListener:NetworkFailedListener {
    fun getLoanEnquiryListSuccess(iGetLoanEnquiryListMainResponse: GetLoanEnquiryListMainResponse)
    fun getLoanEnquiryListFailure(iGetLoanEnquiryListMainResponse: GetLoanEnquiryListMainResponse)
}