package com.vastu.enquiry.property.model.response


import com.google.gson.annotations.SerializedName

data class GetPropertyEnquiryListMainResponse(
    @SerializedName("EnquiryDataResponse")
    val enquiryDataResponse: EnquiryDataResponse,
    @SerializedName("GetEnquiryDetailsResponse")
    val getEnquiryDetailsResponse: GetEnquiryDetailsResponse
)