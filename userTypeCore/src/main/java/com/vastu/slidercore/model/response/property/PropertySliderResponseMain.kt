package com.vastu.slidercore.model.response.property


import com.google.gson.annotations.SerializedName

data class PropertySliderResponseMain(
    @SerializedName("GetPropertySliderImagesResponse")
    val getPropertySliderImagesResponse: GetPropertySliderImagesResponse,
    @SerializedName("PropertySliderImagesResponse")
    val propertySliderImagesResponse: PropertySliderImagesResponse
)