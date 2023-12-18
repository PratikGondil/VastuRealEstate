package com.vastu.realCreator.realCreatorList.model

import com.google.gson.annotations.SerializedName
import com.vastu.propertycore.model.response.ResponseStatusHeader
import java.io.Serializable

data class RealCreatorResponse(
    @SerializedName("ResponseStatusHeader")
    val responseStatusHeader: ResponseStatusHeader
): Serializable
