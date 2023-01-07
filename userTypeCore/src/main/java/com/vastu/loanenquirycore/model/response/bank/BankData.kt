package com.vastu.loanenquirycore.model.response.bank


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BankData(
    @SerializedName("bank_id")val bankId: String?=null,
    @SerializedName("bank_name")val bankName: String?=null
): Serializable {
    override fun toString(): String {
        return "$bankName"
    }
}