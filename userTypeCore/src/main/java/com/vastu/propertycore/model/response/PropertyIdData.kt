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
    @SerializedName("furnishing")
    val furnishing: String,
    @SerializedName("construction_status")
    val construction_status: String,
    @SerializedName("flat_facing")
    val flatFacing: String,
    @SerializedName("area_min")
    val area_min: String,
    @SerializedName("area_max")
    val area_max: String,
    @SerializedName("builder_name")
    val builder_name: String,
    @SerializedName("site_name")
    val site_name: String,
    @SerializedName("rera")
    val rera:String,
    @SerializedName("brief")
    val brief:String,
    @SerializedName("contsruction_name")
    val contsruction_name:String,
    @SerializedName("sub_area")
    val subArea:String,
    @SerializedName("builder_overview")
    val builderOverview:String,
    @SerializedName("is_builder")
    val isBuilder:Boolean,
    @SerializedName("contact_no")
    val contactNno:String,
    @SerializedName("office_address")
    val officeAddress:String




):java.io.Serializable