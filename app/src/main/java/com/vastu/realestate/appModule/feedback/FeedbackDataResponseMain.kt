package com.vastu.realestate.appModule.feedback

import com.google.gson.annotations.SerializedName
import com.vastu.realestate.logincore.model.response.ObjLoginResponse

data class FeedbackDataResponseMain(
    @SerializedName("FeedbackResponse") var objFeedbackResponse : ObjFeedbackResponse,
    @SerializedName("FeedbackDetails") var objFeedbackDtls : ObjFeedbackDtls
)
