package com.vastu.offers.model.response


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OfferData(
    @SerializedName("discount")
    val discount: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("offer_id")
    val offerId: String,
    @SerializedName("property")
    val `property`: String
):Serializable