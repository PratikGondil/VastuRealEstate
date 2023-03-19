package com.vastu.termsandconditions.model.respone


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GettermsConditionDetailsResponse(
    @SerializedName("terms_condition_Data")
    val termsConditionData: List<TermsConditionData>
):Serializable