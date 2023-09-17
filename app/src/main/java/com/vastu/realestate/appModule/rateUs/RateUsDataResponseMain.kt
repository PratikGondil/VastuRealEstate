package com.vastu.realestate.appModule.rateUs

import com.google.gson.annotations.SerializedName

data class RateUsDataResponseMain(
    @SerializedName("RateUsResponse") var objRateUsResponse : ObjRateUsResponse,
    @SerializedName("RateUsDetails") var objRateUsDtls : ObjRateUsDtls
)
