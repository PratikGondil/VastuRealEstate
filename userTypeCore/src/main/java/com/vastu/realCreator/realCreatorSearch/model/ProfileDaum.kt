package com.vastu.realCreator.realCreatorSearch.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProfileDaum(
    @SerializedName("profile_id")
    val profileId: String,
    @SerializedName("profile_name")
    val profileName: String,
): Serializable
