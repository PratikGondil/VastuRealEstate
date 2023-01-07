package com.vastu.realestatecore.model.request

import com.google.gson.annotations.SerializedName

data class ObjBudgetLimits (
    @SerializedName("lowerLimit")
    var lowerLimit :String? = null,
    @SerializedName("upperLimit")
    var upperLimit :String? = null
        )
