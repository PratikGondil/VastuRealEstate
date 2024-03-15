package com.vastu.propertycore.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RelatedProperty(
    @SerializedName("property_id")
    val propertyId: String="",
    @SerializedName("property_title")
    val propertyTitle: String="",
    @SerializedName("property_type")
    val propertyType: String="",
    @SerializedName("property_thumbnail")
    val propertyThumbnail: String="",
    @SerializedName("sell_type")
    val sellType: String="",
    val state: String="",
    val address: String="",
    val price: String="",
    @SerializedName("booking_amount")
    val bookingAmount: String="",
    val bedroom: String="",
    val bathroom: String="",
    val kitchen: String="",
    val garage: String="",
    @SerializedName("swimming_pool")
    val swimmingPool: String="",
    val balcony: String="",
    val floors: String="",
    val area: String="",
    @SerializedName("flat_facing")
    val flatFacing: String="",
    @SerializedName("property_area")
    val propertyArea: String="",
    val brochure: Any?="",
    val description: String="",
    val highlights: String="",
    val availability: String="",
    @SerializedName("build_year")
    val buildYear: String="",
    val amenities: String="",
    @SerializedName("price_min_word")
    val priceMinWord: String="",
    @SerializedName("price_max_word")
    val priceMaxWord: String="",
    @SerializedName("price_max_digit")
    val priceMaxDigit: String="",
    val rera: Any?="",
    @SerializedName("area_min")
    val areaMin: String="",
    @SerializedName("area_max")
    val areaMax: String="",
    val prime: String="",
    val slug: String="",
    val owner: String="",
    val apartment: String="",
    val building: String="",
    @SerializedName("short_address")
    val shortAddress: String="",
    val status: String="",
    @SerializedName("builder_name")
    val builderName: String="",
    @SerializedName("site_name")
    val siteName: String="",
    @SerializedName("contsruction_name")
    val contsructionName: String="",
    @SerializedName("sold_status")
    val soldStatus: String="",
):Serializable
