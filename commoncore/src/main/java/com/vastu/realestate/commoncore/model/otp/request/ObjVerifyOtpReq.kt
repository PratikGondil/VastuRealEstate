package com.vastu.realestate.commoncore.model.otp.request

import com.google.gson.annotations.SerializedName

data class ObjVerifyOtpReq(
    @SerializedName ("user_id") var userId:String?=null,
    @SerializedName ("otp") var otp :String? = null
)
