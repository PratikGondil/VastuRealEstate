package com.vastu.realestate.appModule.feedback

import com.google.gson.annotations.SerializedName

data class ObjFeedbackDtls(
    @SerializedName("user_id") var userId:String,
    @SerializedName("feedback_select") var feedbackSelect:String,
    @SerializedName("feedback_text") var feedbackText:String
)
