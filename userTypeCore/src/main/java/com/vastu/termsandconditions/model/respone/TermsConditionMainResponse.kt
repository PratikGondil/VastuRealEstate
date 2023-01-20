package com.vastu.termsandconditions.model.respone


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TermsConditionMainResponse(
    @SerializedName("Getterms_condition_DetailsResponse")
    val gettermsConditionDetailsResponse: GettermsConditionDetailsResponse,
    @SerializedName("terms_condition_Response")
    val termsConditionResponse: TermsConditionResponse
):Serializable