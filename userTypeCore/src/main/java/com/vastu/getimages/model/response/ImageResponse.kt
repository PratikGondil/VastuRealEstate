package com.vastu.getimages.model.response


import com.google.gson.annotations.SerializedName
import com.vastu.usertypecore.model.response.ResponseStatusHeader

data class ImageResponse(
    @SerializedName("ResponseStatusHeader")
    val responseStatusHeader: ResponseStatusHeader
):java.io.Serializable