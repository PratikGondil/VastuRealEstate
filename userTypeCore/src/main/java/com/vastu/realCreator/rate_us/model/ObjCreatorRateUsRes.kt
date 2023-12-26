package com.vastu.realCreator.rate_us.model
import com.google.gson.annotations.SerializedName

class ObjCreatorRateUsRes {

    data class CreatorRateUsRes (
        @SerializedName("RateUsResponse")
        val rateUsResponse: RateUsResponse
    )

    data class RateUsResponse (
        @SerializedName("ResponseStatusHeader")
        val responseStatusHeader: ResponseStatusHeader
    )

    data class ResponseStatusHeader (
        val statusDescription: String,
        val statusCode: String
    )
}