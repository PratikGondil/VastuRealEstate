package com.vastu.enquiry.property.callbacks.response

import com.vastu.enquiry.property.model.response.GetPropertyEnquiryListMainResponse
import com.vastu.usertypecore.callbacks.response.NetworkFailedListener

interface IGetPropertyEnquiryListResponseListener :NetworkFailedListener{
    fun getPropertyEnquiryListSuccess(getPropertyEnquiryListMainResponse: GetPropertyEnquiryListMainResponse)
    fun getPropertyEnquiryListFailure(getPropertyEnquiryListMainResponse: GetPropertyEnquiryListMainResponse)
}