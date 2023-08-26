package com.vastu.realestatecore.model.response

import com.google.gson.annotations.SerializedName

data class ObjGetFilterDataResponse(
    @SerializedName("FilterData")
    var filteredPropertyResponse:List<PropertyData>,
    @SerializedName("Ad_Slider")
    val adSlider: List<AdSlider>
)
