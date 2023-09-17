package com.vastu.propertycore.model.response

import com.google.gson.annotations.SerializedName
import com.vastu.usertypecore.model.response.ResponseStatusHeader
import java.io.Serializable

data class Photo(
    @SerializedName("photos")
    val photos: String
):Serializable