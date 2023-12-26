package com.vastu.realCreator.realCreatorList.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetRealCreatorDetailsResponse(
    @SerializedName("RealCreatorData")
    val realCreatorData: List<RealCreatorDatum>
):Serializable
