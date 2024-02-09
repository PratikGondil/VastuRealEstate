package com.vastu.realCreator.realCreatorList.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RealCreatorDatum(
    @SerializedName("real_creator_id")
    val realCreatorID: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("profile_id")
    val profileID: String,
    @SerializedName("profile_name")
    val profileName: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("whatsapp")
    val whatsapp: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("taluka_id")
    val talukaID: String,
    @SerializedName("subarea_id")
    val subareaID: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("rating")
    val rating: String,
    @SerializedName( "total_rating")
    val totalRating: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("id")
    var id       : String?  = "",
    @SerializedName("slider")
    var slider   : String?  = "",
    @SerializedName("link")
    var link     : String?  = "",
    @SerializedName("position")
    var position : String?  = "",
    @SerializedName("video")
    var video    : Boolean? = false,
    @SerializedName("is_ad")
    var isAdd    : Boolean? = false
):Serializable{
    override fun toString(): String {
        return "$profileName"
    }
}
