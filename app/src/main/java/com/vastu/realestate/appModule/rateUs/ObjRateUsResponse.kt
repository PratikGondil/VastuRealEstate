package com.vastu.realestate.appModule.rateUs

import com.google.gson.annotations.SerializedName
import com.vastu.realestate.commoncore.model.otp.response.ObjResponseStatusHdr

data class ObjRateUsResponse (
    @SerializedName("ResponseStatusHeader") var objResponseStatusHdr: ObjResponseStatusHdr
)