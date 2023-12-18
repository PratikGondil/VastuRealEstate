package com.vastu.realCreator.realCreatorList.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ObjRealCreatorListReq(
    @SerializedName("language")
    var language: String? = null,
    @SerializedName("profile_id")
    var profileId: String? = null
): Serializable
