package com.vastu.realestatecore.model.response

import com.google.gson.annotations.SerializedName

data class ObjFilterDataResponseMain(
    @SerializedName("FilterDataResponse")
    var objfilterDataResponse: ObjFilterDataResponse,
    @SerializedName("GetFilterDataResponse")
    var objGetFilterDataResponse:ObjGetFilterDataResponse
)
