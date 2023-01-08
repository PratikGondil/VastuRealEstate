package com.vastu.addproperty.model.response


import com.google.gson.annotations.SerializedName

data class RegisterDetails(
    @SerializedName("property_id")
    val propertyId: Int
)