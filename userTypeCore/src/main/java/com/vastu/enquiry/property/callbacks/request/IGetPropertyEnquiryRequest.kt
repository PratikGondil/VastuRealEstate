package com.vastu.enquiry.property.callbacks.request

import android.content.Context
import com.vastu.enquiry.property.callbacks.response.IGetPropertyEnquiryListResponseListener

interface IGetPropertyEnquiryRequest {
    fun callGetPropertyEnquiryList(context: Context, urlEndPoint:String, iGetPropertyEnquiryListResponseListener: IGetPropertyEnquiryListResponseListener)
}