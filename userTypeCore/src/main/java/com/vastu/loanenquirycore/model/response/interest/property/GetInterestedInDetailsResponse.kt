package com.vastu.loanenquirycore.model.response.interest.property


import com.google.gson.annotations.SerializedName

data class GetInterestedInDetailsResponse(
    @SerializedName("InterestedInData")
    val interestedInData: List<InterestedInData>
)