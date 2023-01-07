package com.vastu.enquiry.property.model.response


import com.google.gson.annotations.SerializedName

data class EnquiryData(
    @SerializedName("address")
    val address: String,
    @SerializedName("area")
    val area: String,
    @SerializedName("budget")
    val budget: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("interested_in")
    val interestedIn: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("middle_name")
    val middleName: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("occupation")
    val occupation: String,
    @SerializedName("ownership")
    val ownership: String,
    @SerializedName("property_id")
    val propertyId: String
):java.io.Serializable