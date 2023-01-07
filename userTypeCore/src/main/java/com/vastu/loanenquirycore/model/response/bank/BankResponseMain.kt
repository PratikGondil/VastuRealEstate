package com.vastu.loanenquirycore.model.response.bank


import com.google.gson.annotations.SerializedName

data class BankResponseMain(
    @SerializedName("bankResponse")
    val bankResponse: BankResponse,
    @SerializedName("GetbankDetailsResponse")
    val getbankDetailsResponse: GetbankDetailsResponse
)