package com.vastu.slidercore.model.response


import com.google.gson.annotations.SerializedName

data class GetPropertySliderImagesResponse(
    @SerializedName("PropertySliderImages")
    val propertySliderImages: List<PropertySliderImage>
)