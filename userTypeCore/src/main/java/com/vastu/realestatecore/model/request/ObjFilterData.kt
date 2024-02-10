package com.vastu.realestatecore.model.request

import com.google.gson.annotations.SerializedName

data class ObjFilterData(
    @SerializedName("taluka")
    var city:ArrayList<String> = arrayListOf(),
    @SerializedName("subArea")
    var subAreaId:ArrayList<String> = arrayListOf(),
    @SerializedName("budget")
    var budget:ArrayList<String> = arrayListOf(),
    @SerializedName("propertyType")
    var propertyType: ArrayList<String> = arrayListOf(),
    @SerializedName("pricePerSqFt")
    var pricePerSqFt:ObjPricePerSqFtLimits? = null,
    @SerializedName("noOfBedrooms")
    var noOfBedrooms: ArrayList<String> = arrayListOf(),
    @SerializedName("noOfBathrooms")
    var noOfBathrooms: ArrayList<String> = arrayListOf(),
//    @SerializedName("furnishingStatus")
//    var furnishingStatus : ArrayList<String> = arrayListOf(),
//    @SerializedName("constructionStatus")
//    var constructionStatus : ArrayList<String> = arrayListOf(),
    @SerializedName("listedBy")
    var listedBy: ArrayList<String> = arrayListOf(),
    @SerializedName("buildUpArea")
    var buildUpArea:ArrayList<String> = arrayListOf(),
    @SerializedName("sortBy")
    var sortBy: ArrayList<String> = arrayListOf(),
    @SerializedName("language")
    var language:String = "",
)
