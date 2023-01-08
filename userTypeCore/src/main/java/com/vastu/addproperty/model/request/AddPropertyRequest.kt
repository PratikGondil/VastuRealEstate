package com.vastu.addproperty.model.request


import com.google.gson.annotations.SerializedName

data class AddPropertyRequest(
    @SerializedName("user_id")val userId: String?=null,
    @SerializedName("user_type")val userType: String?=null,

    @SerializedName("property_title")val propertyTitle: String?=null,
    @SerializedName("property_type")val propertyType: String?=null,
    @SerializedName("sell_type")val sellType: String?=null,
    @SerializedName("build_year")val buildYear: String?=null,

    @SerializedName("state")val state: String?=null,
    @SerializedName("city_id")val cityId: String?=null,
    @SerializedName("area_id")val areaId: String?=null,
    @SerializedName("address")val address: String?=null,

    @SerializedName("area")val area: String?=null,
    @SerializedName("price")val price: String?=null,
    @SerializedName("booking_amount")val bookingAmount: String?=null,

    @SerializedName("bathroom")val bathroom: String?=null,
    @SerializedName("bedroom")val bedroom: String?=null,
    @SerializedName("kitchen")val kitchen: String?=null,
    @SerializedName("balcony")val balcony: String?=null,
    @SerializedName("swimming_pool")val swimmingPool: String?=null,
    @SerializedName("garage")val garage: String?=null,
    @SerializedName("floors")val floors: String?=null,

    @SerializedName("brochure")val brochure: String?=null,
    @SerializedName("thumbnail")val thumbnail:String?=null,

    @SerializedName("description")val description: String?=null,
    @SerializedName("highlights")val highlights: String?=null,

    @SerializedName("availability")val availability: String?=null,
    @SerializedName("amenities") val amenities: String?=null,

    @SerializedName("slug")val slug:String?=null,
    @SerializedName("status")val status:String?=null,

    @SerializedName("img1")val img1: String?=null,
    @SerializedName("img2")val img2: String?=null,
    @SerializedName("img3")val img3: String?=null,
    @SerializedName("img4")val img4: String?=null,
    @SerializedName("img5")val img5: String?=null

):java.io.Serializable