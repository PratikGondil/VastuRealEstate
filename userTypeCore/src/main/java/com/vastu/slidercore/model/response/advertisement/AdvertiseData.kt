package com.vastu.slidercore.model.response.advertisement


import com.google.gson.annotations.SerializedName

data class AdvertiseData(
    @SerializedName("ad_slider")
    val adSlider: String,
    @SerializedName("ad_slider_id")
    val adSliderId: String,
    @SerializedName("ad_text")
    val ad_text: String

)