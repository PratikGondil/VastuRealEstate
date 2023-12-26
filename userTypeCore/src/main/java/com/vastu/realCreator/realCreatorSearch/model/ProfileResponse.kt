package com.vastu.realCreator.realCreatorSearch.model

import com.google.gson.annotations.SerializedName
import com.vastu.propertycore.model.response.ResponseStatusHeader
import java.io.Serializable

data class ProfileResponse(
    @SerializedName("ResponseStatusHeader")
    val responseStatusHeader: ResponseStatusHeader,
) : Serializable