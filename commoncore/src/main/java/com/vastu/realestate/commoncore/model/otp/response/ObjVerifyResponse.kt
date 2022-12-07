package com.vastu.realestate.commoncore.model.otp.response

import com.google.gson.annotations.SerializedName

data class ObjVerifyResponse (
    @SerializedName ("ResponseStatusHeader") var objResponseStatusHdr: ObjResponseStatusHdr
        )
