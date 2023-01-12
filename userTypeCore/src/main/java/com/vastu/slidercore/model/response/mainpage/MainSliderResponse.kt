package com.vastu.slidercore.model.response.mainpage


import com.google.gson.annotations.SerializedName
import com.vastu.usertypecore.model.response.ResponseStatusHeader

data class MainSliderResponse(
    @SerializedName("ResponseStatusHeader")
    val responseStatusHeader: ResponseStatusHeader
)