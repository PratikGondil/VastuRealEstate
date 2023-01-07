package com.vastu.loanenquirycore.model.response.occupation


import com.google.gson.annotations.SerializedName

data class OccupationMainResponse(
    @SerializedName("GetoccupationDetailsResponse")
    val getoccupationDetailsResponse: GetoccupationDetailsResponse,
    @SerializedName("occupationResponse")
    val occupationResponse: OccupationResponse
)