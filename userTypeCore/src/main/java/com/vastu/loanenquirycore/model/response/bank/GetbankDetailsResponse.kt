package com.vastu.loanenquirycore.model.response.bank


import com.google.gson.annotations.SerializedName

data class GetbankDetailsResponse(
    @SerializedName("bankData")
    val bankData: List<BankData>
)