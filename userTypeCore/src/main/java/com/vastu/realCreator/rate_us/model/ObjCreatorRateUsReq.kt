package com.vastu.realCreator.rate_us.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ObjCreatorRateUsReq(
    @SerializedName("user_id")
    val userID: String,

    @SerializedName("rate_us")
    val rateUs: String,

    @SerializedName("real_creator_id")
    val realCreatorID: String
):Serializable
