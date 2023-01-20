package com.vastu.offers.model.response


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetofferDetailsResponse(
    @SerializedName("offerData")
    val offerData: List<OfferData>
):Serializable