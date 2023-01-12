package com.vastu.slidercore.model.response.mainpage


import com.google.gson.annotations.SerializedName

data class MainPageSliderResponse(
    @SerializedName("GetMainSliderDetailsResponse")
    val getMainSliderDetailsResponse: GetMainSliderDetailsResponse,
    @SerializedName("MainSliderResponse")
    val mainSliderResponse: MainSliderResponse
):java.io.Serializable