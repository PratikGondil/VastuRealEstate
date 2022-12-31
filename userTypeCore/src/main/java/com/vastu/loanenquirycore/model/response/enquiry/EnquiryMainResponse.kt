package com.vastu.loanenquirycore.model.response.enquiry


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EnquiryMainResponse(
    @SerializedName("RegisterResponse")
    val registerResponse: RegisterResponse
): Serializable