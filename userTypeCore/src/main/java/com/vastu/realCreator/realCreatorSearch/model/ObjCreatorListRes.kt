package com.vastu.realCreator.realCreatorSearch.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ObjCreatorListRes(
    @SerializedName("ProfileResponse")
    val profileResponse: ProfileResponse,
    @SerializedName("GetProfileDetailsResponse")
    val getProfileDetailsResponse: GetProfileDetailsResponse,
):Serializable
