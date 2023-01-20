package com.vastu.offers.model.response


import com.google.gson.annotations.SerializedName
import com.vastu.usertypecore.model.response.ResponseStatusHeader

data class OfferResponse(
    @SerializedName("ResponseStatusHeader")
    val responseStatusHeader: ResponseStatusHeader
)