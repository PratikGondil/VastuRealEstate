package com.vastu.enquiry.property.callbacks.request

import com.vastu.enquiry.property.callbacks.response.IGetPropertyEnquiryListResponseListener

interface IGetPropertyEnquiryRequest {
    fun callGetPropertyEnquiryList(urlEndPoint:String,iGetPropertyEnquiryListResponseListener: IGetPropertyEnquiryListResponseListener)
}