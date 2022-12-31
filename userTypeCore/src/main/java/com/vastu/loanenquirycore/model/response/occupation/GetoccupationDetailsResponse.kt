package com.vastu.loanenquirycore.model.response.occupation


import com.google.gson.annotations.SerializedName

data class GetoccupationDetailsResponse(
    @SerializedName("occupationData")
    val occupationData: List<OccupationData>
)