package com.vastu.getimages.model.response


import com.google.gson.annotations.SerializedName

data class GetImageMainResponse(
    @SerializedName("GetImageDetailsResponse")
    val getImageDetailsResponse: GetImageDetailsResponse,
    @SerializedName("ImageResponse")
    val imageResponse: ImageResponse
):java.io.Serializable