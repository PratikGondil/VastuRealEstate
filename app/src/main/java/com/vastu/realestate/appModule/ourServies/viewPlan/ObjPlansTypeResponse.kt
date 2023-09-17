package com.vastu.realestate.appModule.ourServies.viewPlan

import com.google.gson.annotations.SerializedName
import com.vastu.realestate.commoncore.model.otp.response.ObjResponseStatusHdr

data class ObjPlansTypeResponse(
    @SerializedName("ResponseStatusHeader") var objResponseStatusHdr: ObjResponseStatusHdr
)
