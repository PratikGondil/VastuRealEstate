package com.vastu.realestatecore.model.request

import com.google.gson.annotations.SerializedName

data class ObjFilterData(
    @SerializedName("subArea")
    var subAreaId:ArrayList<String> = arrayListOf(),
    @SerializedName("budget")
    var budget:ObjBudgetLimits? = null,
    @SerializedName("propertyType")
    var propertyType: ArrayList<String> = arrayListOf(),
    @SerializedName("pricePerSqFt")
    var pricePerSqFt:ObjPricePerSqFtLimits? = null,
    @SerializedName("noOfBedrooms")
    var noOfBedrooms: ArrayList<String> = arrayListOf(),
    @SerializedName("noOfBathrooms")
    var noOfBathrooms: ArrayList<String> = arrayListOf(),
    @SerializedName("furnishingStatus")
    var furnishingStatus : ArrayList<String> = arrayListOf(),
    @SerializedName("constructionStatus")
    var constructionStatus : ArrayList<String> = arrayListOf(),
    @SerializedName("listedBy")
    var listedBy: ArrayList<String> = arrayListOf(),
    @SerializedName("buildUpArea")
    var buildUpArea:ObjBuildUpAreaLimits? = null,
    @SerializedName("sortBy")
    var sortBy: ArrayList<String> = arrayListOf(),
)
