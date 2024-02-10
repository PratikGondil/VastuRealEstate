package com.vastu.realCreator.creatorDetails.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ObjDetailsCreatorRes(
    @SerializedName("SingalRealCreatorResponse")
    val singalRealCreatorResponse: SingalRealCreatorResponse,
    @SerializedName("GetSingalRealCreatorDetailsResponse")
    val getSingalRealCreatorDetailsResponse: GetSingalRealCreatorDetailsResponse
):Serializable



data class GetSingalRealCreatorDetailsResponse(
    @SerializedName("SingalRealCreatorData")
    val singalRealCreatorData: List<SingalRealCreatorDatum>,
    @SerializedName("slider")
    val slider: List<Slider>
):Serializable

data class SingalRealCreatorDatum(
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
    @SerializedName("total_rating")
    val totalRating: String,
    @SerializedName("slug")
    val slug: String,
):Serializable

data class Slider(
    val id: String,
    val image: String,
    val video: Boolean
):Serializable


data class SingalRealCreatorResponse(
    @SerializedName("ResponseStatusHeader")
    val responseStatusHeader: ResponseStatusHeader
):Serializable

data class ResponseStatusHeader(
    @SerializedName("statusDescription")
    val statusDescription: String,
    @SerializedName("statusCode")
    val statusCode: String
):Serializable
