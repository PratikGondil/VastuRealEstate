package com.vastu.realCreator.creatorDetails.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ObjDetailsCreatorReq(
    @SerializedName("language")
    var language: String? = null,
    @SerializedName("real_creator_id")
    var realCreatorId: String? = null,
):Serializable
