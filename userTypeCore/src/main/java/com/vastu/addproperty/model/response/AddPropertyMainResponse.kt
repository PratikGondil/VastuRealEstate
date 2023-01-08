package com.vastu.addproperty.model.response


import com.google.gson.annotations.SerializedName

data class AddPropertyMainResponse(
    @SerializedName("RegisterDetails")
    val registerDetails: RegisterDetails,
    @SerializedName("RegisterResponse")
    val registerResponse: RegisterResponse
)