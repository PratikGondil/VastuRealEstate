package com.vastu.slidercore.model.response.advertisement


import com.google.gson.annotations.SerializedName

data class GetAdvertiseDetailsResponse(
    @SerializedName("AdvertiseData")
    val advertiseData: List<AdvertiseData>
)