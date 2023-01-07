package com.vastu.enquiry.loan.callback.request

import android.content.Context
import com.vastu.enquiry.loan.callback.response.IGetLoanEnquiryListResponseListener
import com.vastu.enquiry.property.callbacks.response.IGetPropertyEnquiryListResponseListener

interface IGetLoanEnquiryRequest {
    fun callGetLoanEnquiryList(context: Context, urlEndPoint:String, iGetLoanEnquiryListResponseListener: IGetLoanEnquiryListResponseListener)
}