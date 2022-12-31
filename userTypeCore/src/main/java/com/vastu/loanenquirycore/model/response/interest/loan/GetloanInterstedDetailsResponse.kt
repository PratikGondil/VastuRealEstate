package com.vastu.loanenquirycore.model.response.interest.loan


import com.google.gson.annotations.SerializedName

data class GetloanInterstedDetailsResponse(
    @SerializedName("loanInterstedData")
    val loanInterstedData: List<LoanInterstedData>
)