package com.vastu.usertypecore.model.response

import com.google.gson.annotations.SerializedName

data class ResponseStatusHeader(
    @SerializedName("statusCode")
    val statusCode: String,
    @SerializedName("statusDescription")
    val statusDescription: String
)