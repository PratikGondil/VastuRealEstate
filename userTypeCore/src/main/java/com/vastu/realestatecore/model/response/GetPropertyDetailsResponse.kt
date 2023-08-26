package com.vastu.realestatecore.model.response


import com.google.gson.annotations.SerializedName

data class GetPropertyDetailsResponse(
    @SerializedName("PropertyData")
    val propertyData: List<PropertyData>,
    @SerializedName("Ad_Slider")
    val adSlider: List<AdSlider>
)