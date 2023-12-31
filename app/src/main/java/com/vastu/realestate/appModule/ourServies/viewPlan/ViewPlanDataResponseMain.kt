package com.vastu.realestate.appModule.ourServies.viewPlan

import com.google.gson.annotations.SerializedName

data class ViewPlanDataResponseMain(
    @SerializedName("PlansResponse") var objPlansResponse : ObjViewPlanResponse,
    @SerializedName("PlansDetails") var objPlansDtls : ObjViewPlanDtls
)
