package com.vastu.getimages.model.response


import com.google.gson.annotations.SerializedName

data class GetImageDetailsResponse(
    @SerializedName("ImageData")val imageData: List<ImageData>
):java.io.Serializable