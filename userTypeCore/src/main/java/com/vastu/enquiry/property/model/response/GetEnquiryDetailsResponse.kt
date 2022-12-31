package com.vastu.enquiry.property.model.response


import com.google.gson.annotations.SerializedName

data class GetEnquiryDetailsResponse(
    @SerializedName("EnquiryData")
    val enquiryData: List<EnquiryData>
)