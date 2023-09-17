package com.vastu.realestate.appModule.feedback

import com.google.gson.annotations.SerializedName
import com.vastu.realestate.commoncore.model.otp.response.ObjResponseStatusHdr

data class ObjFeedbackResponse(
    @SerializedName("ResponseStatusHeader") var objResponseStatusHdr: ObjResponseStatusHdr
)
