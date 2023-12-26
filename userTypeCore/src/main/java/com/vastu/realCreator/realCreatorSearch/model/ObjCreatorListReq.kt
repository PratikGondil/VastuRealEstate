package com.vastu.realCreator.realCreatorSearch.model

import com.google.gson.annotations.SerializedName

data class ObjCreatorListReq(
    @SerializedName("language")
    var language: String? = null,
    @SerializedName("real_creator_id")
    var realCreatorId: String? = null
)
