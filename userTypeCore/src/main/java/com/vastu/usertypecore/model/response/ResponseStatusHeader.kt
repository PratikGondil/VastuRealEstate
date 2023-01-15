package com.vastu.usertypecore.model.response

import com.google.gson.annotations.SerializedName

data class ResponseStatusHeader(
    @SerializedName("statusCode")
    val statusCode: String? = null,
    @SerializedName("statusDescription")
    val statusDescription: String?=null
)