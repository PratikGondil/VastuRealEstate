package com.vastu.realestate.logincore.model.request

import com.google.gson.annotations.SerializedName

data class ObjLoginReq(
    @SerializedName("mobile") var mobileNumber :String
)
