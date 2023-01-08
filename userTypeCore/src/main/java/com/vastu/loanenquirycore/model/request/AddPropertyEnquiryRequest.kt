package com.vastu.loanenquirycore.model.request


import com.google.gson.annotations.SerializedName
import com.vastu.propertycore.model.response.PropertyIdData
import java.io.Serializable

data class AddPropertyEnquiryRequest(
    @SerializedName("property_id")val propertyId: String?=null,
    @SerializedName("first_name")val firstName: String?=null,
    @SerializedName("middle_name")val middleName: String?=null,
    @SerializedName("last_name")val lastName: String?=null,
    @SerializedName("mobile")val mobile: String?=null,
    @SerializedName("address")val address: String?=null,
    @SerializedName("occupation")val occupation: String?=null,
    @SerializedName("interested_in")val interestedIn: String?=null,
    @SerializedName("ownership")val ownership: String?=null,
    @SerializedName("area")val area: String?=null,
    @SerializedName("budget")val budget: String?=null,
): Serializable