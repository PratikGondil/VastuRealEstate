package com.vastu.enquiry.loan.callback.request

import com.vastu.enquiry.loan.callback.response.IGetLoanEnquiryListResponseListener
import com.vastu.enquiry.property.callbacks.response.IGetPropertyEnquiryListResponseListener

interface IGetLoanEnquiryRequest {
    fun callGetLoanEnquiryList(urlEndPoint:String,iGetLoanEnquiryListResponseListener: IGetLoanEnquiryListResponseListener)
}