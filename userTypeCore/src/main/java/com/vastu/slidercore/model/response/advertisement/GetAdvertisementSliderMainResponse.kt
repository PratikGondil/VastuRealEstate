package com.vastu.slidercore.model.response.advertisement


import com.google.gson.annotations.SerializedName

data class GetAdvertisementSliderMainResponse(
    @SerializedName("AdvertiseResponse")
    val advertiseResponse: AdvertiseResponse,
    @SerializedName("GetAdvertiseDetailsResponse")
    val getAdvertiseDetailsResponse: GetAdvertiseDetailsResponse
)