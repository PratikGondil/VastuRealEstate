package com.vastu.offers.model.response


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OffersMainResponse(
    @SerializedName("GetofferDetailsResponse")
    val getofferDetailsResponse: GetofferDetailsResponse,
    @SerializedName("offerResponse")
    val offerResponse: OfferResponse
):Serializable