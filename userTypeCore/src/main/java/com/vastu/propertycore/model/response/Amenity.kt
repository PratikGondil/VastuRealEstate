package com.vastu.propertycore.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Amenity(
    @SerializedName("icon")
    val icon: String,
    @SerializedName("amenities_name")
    val amenities_name: String,
):Serializable