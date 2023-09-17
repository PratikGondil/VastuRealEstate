package com.vastu.realestate.appModule.feedback

import com.google.gson.annotations.SerializedName

data class GetFeedbackRequest(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("feedback_select")
    val feedbackSelect: String,
    @SerializedName("feedback_text")
    val feedbackText: String
):java.io.Serializable

