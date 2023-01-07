package com.vastu.realestate.appModule.enquirylist.uiinterfaces

import com.vastu.enquiry.property.model.response.GetPropertyEnquiryListMainResponse

interface IPropertyListListener:INetworkFailListener {
    fun onSuccessGetPropertyEnquiry(getPropertyEnquiryListMainResponse: GetPropertyEnquiryListMainResponse)
    fun onFailureGetPropertyEnquiry(getPropertyEnquiryListMainResponse: GetPropertyEnquiryListMainResponse)
}