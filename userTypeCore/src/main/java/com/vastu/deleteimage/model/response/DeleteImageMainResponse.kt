package com.vastu.deleteimage.model.response


import com.google.gson.annotations.SerializedName
import com.vastu.deleteimage.model.response.ImageDeleteResponse

data class DeleteImageMainResponse(
    @SerializedName("ImageDeleteResponse")
    val imageDeleteResponse: ImageDeleteResponse
):java.io.Serializable