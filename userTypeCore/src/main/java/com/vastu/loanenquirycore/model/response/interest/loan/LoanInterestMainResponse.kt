package com.vastu.loanenquirycore.model.response.interest.loan


import com.google.gson.annotations.SerializedName

data class LoanInterestMainResponse(
    @SerializedName("GetloanInterstedDetailsResponse")
    val getloanInterstedDetailsResponse: GetloanInterstedDetailsResponse,
    @SerializedName("loanInterstedResponse")
    val loanInterstedResponse: LoanInterstedResponse
)