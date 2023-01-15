package com.vastu.getimages.model.request


import com.google.gson.annotations.SerializedName

data class GetImageRequest(
    @SerializedName("property_id")val propertyId: String?=null
):java.io.Serializable