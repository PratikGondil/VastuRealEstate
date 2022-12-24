package com.vastu.propertycore.model.response


import com.google.gson.annotations.SerializedName

data class PropertyDataResponseMain(
    @SerializedName("GetPropertyIdDetailsResponse")
    val getPropertyIdDetailsResponse: GetPropertyIdDetailsResponse,
    @SerializedName("PropertyIdResponse")
    val propertyIdResponse: PropertyIdResponse
)