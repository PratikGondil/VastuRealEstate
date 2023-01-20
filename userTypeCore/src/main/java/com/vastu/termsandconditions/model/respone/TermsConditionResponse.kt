package com.vastu.termsandconditions.model.respone


import com.google.gson.annotations.SerializedName
import com.vastu.usertypecore.model.response.ResponseStatusHeader
import java.io.Serializable

data class TermsConditionResponse(
    @SerializedName("ResponseStatusHeader")
    val responseStatusHeader: ResponseStatusHeader
):Serializable