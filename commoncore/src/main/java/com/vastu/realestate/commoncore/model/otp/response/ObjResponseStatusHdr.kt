package com.vastu.realestate.commoncore.model.otp.response

import com.google.gson.annotations.SerializedName

data class ObjResponseStatusHdr (
    @SerializedName("statusDescription") var statusDescr:String,
    @SerializedName("statusCode") var statusCode:String,

    )

