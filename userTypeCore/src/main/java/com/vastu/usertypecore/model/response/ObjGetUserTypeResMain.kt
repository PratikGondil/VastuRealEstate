package com.vastu.usertypecore.model.response


import com.google.gson.annotations.SerializedName
import com.vastu.realestatecore.model.response.GetUserTypeDataDetailsResponse

data class ObjGetUserTypeResMain(
    @SerializedName("GetUserTypeDataDetailsResponse")
    val getUserTypeDataDetailsResponse: GetUserTypeDataDetailsResponse,
    @SerializedName("UserTypeResponse")
    val userTypeResponse: UserTypeResponse
)