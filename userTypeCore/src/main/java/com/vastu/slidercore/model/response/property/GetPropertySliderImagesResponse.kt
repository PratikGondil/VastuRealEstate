package com.vastu.slidercore.model.response.property


import com.google.gson.annotations.SerializedName
import com.vastu.slidercore.model.response.property.PropertySliderImage

data class GetPropertySliderImagesResponse(
    @SerializedName("PropertySliderImages")
    val propertySliderImages: List<PropertySliderImage>
)