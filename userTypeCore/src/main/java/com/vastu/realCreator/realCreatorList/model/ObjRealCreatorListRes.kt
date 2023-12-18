package com.vastu.realCreator.realCreatorList.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ObjRealCreatorListRes(
    @SerializedName("RealCreatorResponse")
    val realCreatorResponse: RealCreatorResponse,

    @SerializedName("GetRealCreatorDetailsResponse")
    val getRealCreatorDetailsResponse: GetRealCreatorDetailsResponse
):Serializable
