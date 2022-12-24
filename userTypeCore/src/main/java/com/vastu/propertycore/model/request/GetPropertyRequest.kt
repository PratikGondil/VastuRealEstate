package com.vastu.propertycore.model.request


import com.google.gson.annotations.SerializedName

data class GetPropertyRequest(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("property_id")
    val propertyId: String
):java.io.Serializable