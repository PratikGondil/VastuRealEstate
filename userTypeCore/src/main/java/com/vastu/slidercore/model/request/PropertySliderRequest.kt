package com.vastu.slidercore.model.request


import com.google.gson.annotations.SerializedName

data class PropertySliderRequest(
    @SerializedName("property_id")
    val propertyId: String
)