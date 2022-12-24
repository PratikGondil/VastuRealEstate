package com.vastu.realestatecore.model.response


import com.google.gson.annotations.SerializedName
import com.vastu.realestatecore.model.response.GetPropertyDetailsResponse
import com.vastu.realestatecore.model.response.PropertyResponse

data class ObjGetPropertyListResMain(
    @SerializedName("GetPropertyDetailsResponse")
    val getPropertyDetailsResponse: GetPropertyDetailsResponse,
    @SerializedName("PropertyResponse")
    val propertyResponse: PropertyResponse
)