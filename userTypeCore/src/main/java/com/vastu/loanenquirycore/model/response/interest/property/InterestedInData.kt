package com.vastu.loanenquirycore.model.response.interest.property


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class InterestedInData(
    @SerializedName("pinterest_id")
    val propertyId: String,
    @SerializedName("pinterested_name")
    val propertyName: String
): Serializable {
    override fun toString(): String {
        return "$propertyName"
    }
}