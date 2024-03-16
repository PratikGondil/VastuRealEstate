package com.vastu.realestate.appModule.ourServies.viewPlan

import com.google.gson.annotations.SerializedName

data class GetPlansTypeRequest(
    @SerializedName("plan_type_id")
    val planTypeId: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("profile_id")
    val profileId: String
):java.io.Serializable


