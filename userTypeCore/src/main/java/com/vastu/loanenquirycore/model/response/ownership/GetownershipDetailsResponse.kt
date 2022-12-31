package com.vastu.loanenquirycore.model.response.ownership


import com.google.gson.annotations.SerializedName

data class GetownershipDetailsResponse(
    @SerializedName("ownershipData")
    val ownershipData: List<OwnershipData>
)