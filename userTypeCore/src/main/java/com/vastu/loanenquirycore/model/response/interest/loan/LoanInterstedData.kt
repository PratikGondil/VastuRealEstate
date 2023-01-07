package com.vastu.loanenquirycore.model.response.interest.loan


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoanInterstedData(
    @SerializedName("linterested_id")val loanId: String?=null,
    @SerializedName("linterested_name")val loanName: String?=null
): Serializable {
    override fun toString(): String {
        return "$loanName"
    }
}