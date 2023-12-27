package com.vastu.realCreator.realCreatorSearch.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class GetProfileDetailsResponse(
    @SerializedName("ProfileData")
    val profileData: ArrayList<ProfileDaum>,
): Serializable
