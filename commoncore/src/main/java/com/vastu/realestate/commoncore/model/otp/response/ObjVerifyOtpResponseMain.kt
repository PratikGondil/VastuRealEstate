package com.vastu.realestate.commoncore.model.otp.response

import com.google.gson.annotations.SerializedName

data class ObjVerifyOtpResponseMain(
    @SerializedName ("VerifyResponse") var objVerifyResponse:ObjVerifyResponse
)
