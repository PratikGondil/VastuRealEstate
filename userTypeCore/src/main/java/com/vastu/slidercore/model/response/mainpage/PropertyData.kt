package com.vastu.slidercore.model.response.mainpage


import com.google.gson.annotations.SerializedName

data class PropertyData(
    @SerializedName("image_id")
    val imageId: String,
    @SerializedName("property_image")
    val propertyImage: String
):java.io.Serializable