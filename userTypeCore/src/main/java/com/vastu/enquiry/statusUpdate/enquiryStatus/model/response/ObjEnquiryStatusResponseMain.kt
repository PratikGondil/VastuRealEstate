package com.vastu.enquiry.statusUpdate.enquiryStatus.model.response

import com.google.gson.annotations.SerializedName

data class ObjEnquiryStatusResponseMain(

    @SerializedName("EnquiryStatusResponse") var objEnquiryStatusResponse: ObjEnquiryStatusResponse = ObjEnquiryStatusResponse(),
    @SerializedName("GetEnquiryStatusDetailsResponse") var objGetEnquiryStatusDetailsResponse :ObjGetEnquiryStatusDetailsResponse = ObjGetEnquiryStatusDetailsResponse()
)
