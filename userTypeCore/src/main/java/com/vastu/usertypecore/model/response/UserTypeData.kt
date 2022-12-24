package com.vastu.usertypecore.model.response


import com.google.gson.annotations.SerializedName

data class UserTypeData(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("user_type")
    val userType: String
)