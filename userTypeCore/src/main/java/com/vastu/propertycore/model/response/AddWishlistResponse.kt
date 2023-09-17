package com.vastu.propertycore.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddWishlistResponse(
    @SerializedName("RegisterResponse")
    val registerResponse: RegisterResponse,
):Serializable


data class RegisterResponse(
    @SerializedName("ResponseStatusHeader")
    val responseStatusHeader: ResponseStatusHeader,
):Serializable

data class ResponseStatusHeader(
    @SerializedName("statusDescription")
    val statusDescription: String="",
    @SerializedName("statusCode")
    val statusCode: String=""
):Serializable