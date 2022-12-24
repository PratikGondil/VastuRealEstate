package com.vastu.usertypecore.model.request

import com.google.gson.annotations.SerializedName

data class ObjGetUserTypeReq(
    @SerializedName("user_id")
    val userId: String
)