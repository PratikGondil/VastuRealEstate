package com.vastu.propertycore.model.response


import com.google.gson.annotations.SerializedName

data class PropertyIdData(
    @SerializedName("address")
    val address: String,
    @SerializedName("amenities")
    val amenities: Any,
    @SerializedName("area")
    val area: String,
    @SerializedName("availability")
    val availability: String,
    @SerializedName("balcony")
    val balcony: String,
    @SerializedName("bathroom")
    val bathroom: String,
    @SerializedName("bedroom")
    val bedroom: String,
    @SerializedName("booking_amount")
    val bookingAmount: String,
    @SerializedName("brochure")
    val brochure: String,
    @SerializedName("build_year")
    val buildYear: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("floors")
    val floors: String,
    @SerializedName("garage")
    val garage: String,
    @SerializedName("highlights")
    val highlights: String,
    @SerializedName("kitchen")
    val kitchen: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("property_area")
    val propertyArea: String,
    @SerializedName("property_id")
    val propertyId: String,
    @SerializedName("property_thumbnail")
    val propertyThumbnail: String,
    @SerializedName("property_title")
    val propertyTitle: String,
    @SerializedName("property_type")
    val propertyType: String,
    @SerializedName("sell_type")
    val sellType: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("swimming_pool")
    val swimmingPool: String,
    @SerializedName("price_min_word")
    val priceMinWord: String,
    @SerializedName( "price_max_word")
    val priceMaxWord: String,
    @SerializedName( "price_min_digit")
    val priceMinDigit: String,
    @SerializedName( "price_max_digit")
    val priceMaxDigit: String,
    @SerializedName( "owner")
    val owner: String,
    @SerializedName( "apartment")
    val apartment: String,
    @SerializedName( "building")
    val building: String,
    @SerializedName("short_address")
    val shortAddress: String,
):java.io.Serializable