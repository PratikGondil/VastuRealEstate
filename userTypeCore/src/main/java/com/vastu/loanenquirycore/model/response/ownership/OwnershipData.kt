package com.vastu.loanenquirycore.model.response.ownership


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OwnershipData(
    @SerializedName("ownership_id")
    val ownershipId: String,
    @SerializedName("ownership_name")
    val ownershipName: String
): Serializable {
    override fun toString(): String {
        return "$ownershipName"
    }
}