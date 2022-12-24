package com.vastu.slidercore.model.response


import com.google.gson.annotations.SerializedName

data class PropertySliderImage(
    @SerializedName("image")
    val image: String?,
    @SerializedName("property_id")
    val propertyId: String,
    @SerializedName("slider_id")
    val sliderId: String
)