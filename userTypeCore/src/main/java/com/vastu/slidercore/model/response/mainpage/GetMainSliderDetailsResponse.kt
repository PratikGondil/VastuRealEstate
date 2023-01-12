package com.vastu.slidercore.model.response.mainpage


import com.google.gson.annotations.SerializedName

data class GetMainSliderDetailsResponse(
    @SerializedName("PropertyData")
    val propertyData: List<PropertyData>
):java.io.Serializable