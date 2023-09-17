package com.vastu.realestate.appModule.rateUs

import com.google.gson.annotations.SerializedName

data class GetRateUsRequest(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("rate_us")
    val rateUs: String,
    @SerializedName("feedback")
    val feedback: String
):java.io.Serializable

