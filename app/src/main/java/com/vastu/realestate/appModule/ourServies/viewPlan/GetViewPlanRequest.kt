package com.vastu.realestate.appModule.ourServies.viewPlan

import com.google.gson.annotations.SerializedName

data class GetViewPlanRequest(
    @SerializedName("language")
    val language: String
):java.io.Serializable
