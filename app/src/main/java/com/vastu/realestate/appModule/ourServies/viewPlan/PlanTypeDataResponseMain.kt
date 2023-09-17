package com.vastu.realestate.appModule.ourServies.viewPlan

import com.google.gson.annotations.SerializedName

data class PlanTypeDataResponseMain(
    @SerializedName("PlansTypeResponse") var objPlansTypeResponse : ObjPlansTypeResponse,
    @SerializedName("PlansTypeDetails") var objPlansTypeDtls : ObjPlansTypeDtls
)
