package com.vastu.realestatecore.model.request

import com.google.gson.annotations.SerializedName

data class ObjPricePerSqFtLimits (
    @SerializedName("priceLowerLimit")
    var lowerLimit :String? = null,
    @SerializedName("priceUpperLimit")
    var upperLimit :String? = null
        )
