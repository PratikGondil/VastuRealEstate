package com.vastu.getimages.model.response


import com.google.gson.annotations.SerializedName

data class ImageData(
    @SerializedName("image")
    val image: String,
    @SerializedName("image_id")
    val imageId: String,
    @SerializedName("property_id")
    val propertyId: String
):java.io.Serializable