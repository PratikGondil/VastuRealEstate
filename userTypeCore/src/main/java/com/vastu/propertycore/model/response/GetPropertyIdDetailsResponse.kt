package com.vastu.propertycore.model.response


import com.google.gson.annotations.SerializedName

data class GetPropertyIdDetailsResponse(
    @SerializedName("PropertyIdData")
    val propertyIdData: List<PropertyIdData>,
    @SerializedName("Amenities")
    val amenities: List<Amenity>,
    @SerializedName("RelatedProperty")
    val relatedProperty: List<RelatedProperty>,
)