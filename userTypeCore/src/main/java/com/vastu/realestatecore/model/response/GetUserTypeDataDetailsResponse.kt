package com.vastu.realestatecore.model.response


import com.google.gson.annotations.SerializedName
import com.vastu.usertypecore.model.response.UserTypeData

data class GetUserTypeDataDetailsResponse(
    @SerializedName("UserTypeData")
    val userTypeData: List<UserTypeData>
)