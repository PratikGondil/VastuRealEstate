package com.vastu.loanenquirycore.model.response.interest.property


import com.google.gson.annotations.SerializedName

data class PropertyInterestMainResponse(
    @SerializedName("GetInterestedInDetailsResponse")
    val getInterestedInDetailsResponse: GetInterestedInDetailsResponse,
    @SerializedName("InterestedInResponse")
    val interestedInResponse: InterestedInResponse
)