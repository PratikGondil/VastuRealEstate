package com.vastu.slidercore.model.request


import com.google.gson.annotations.SerializedName

data class MainPagerSliderRequest(
    @SerializedName("area_id")val areaId: String?=null,
    @SerializedName("user_id")val userId: String?=null
):java.io.Serializable