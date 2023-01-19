package com.vastu.enquiry.statusUpdate.enquiryStatus.model.response

import com.google.gson.annotations.SerializedName

data class ObjGetEnquiryStatusDetailsResponse(
    @SerializedName("EnquiryStatusData" ) var objEnquiryStatusData : ArrayList<ObjEnquiryStatusData> = arrayListOf()

)

