package com.vastu.loanenquirycore.model.response.occupation


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OccupationData(
    @SerializedName("occupation_id")val occupationId: String?=null,
    @SerializedName("occupation_name")val occupationName: String?=null
): Serializable {
    override fun toString(): String {
        return "$occupationName"
    }
}