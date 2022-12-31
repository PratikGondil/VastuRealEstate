package com.vastu.loanenquirycore.model.response.ownership


import com.google.gson.annotations.SerializedName

data class OwnershipMainResponse(
    @SerializedName("GetownershipDetailsResponse")
    val getownershipDetailsResponse: GetownershipDetailsResponse,
    @SerializedName("ownershipResponse")
    val ownershipResponse: OwnershipResponse
)