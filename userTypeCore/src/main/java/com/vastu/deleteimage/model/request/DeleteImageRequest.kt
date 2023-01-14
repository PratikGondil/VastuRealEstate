package com.vastu.deleteimage.model.request


import com.google.gson.annotations.SerializedName

data class DeleteImageRequest(
    @SerializedName("image_id") val imageId: String?=null,
    @SerializedName("property_id") val propertyId: String?=null
):java.io.Serializable