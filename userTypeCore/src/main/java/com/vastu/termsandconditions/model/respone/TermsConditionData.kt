package com.vastu.termsandconditions.model.respone


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TermsConditionData(
    @SerializedName("term_condition")
    val termCondition: String,
    @SerializedName("term_id")
    val termId: String
):Serializable