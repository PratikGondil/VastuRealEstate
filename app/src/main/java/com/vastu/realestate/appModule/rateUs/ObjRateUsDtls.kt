package com.vastu.realestate.appModule.rateUs

import com.google.gson.annotations.SerializedName

data class ObjRateUsDtls(
    @SerializedName("user_id") var userId:String,
    @SerializedName("rate_us") var rateUs:String,
    @SerializedName("feedback") var feedback:String
)
