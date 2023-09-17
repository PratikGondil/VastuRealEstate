package com.vastu.realestate.appModule.ourServies.viewPlan

import com.google.gson.annotations.SerializedName

data class GetPlansTypeRequest(
    @SerializedName("plan_type_id")
    val planTypeId: String
):java.io.Serializable


