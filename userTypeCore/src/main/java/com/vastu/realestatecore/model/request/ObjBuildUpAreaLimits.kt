package com.vastu.realestatecore.model.request

import com.google.gson.annotations.SerializedName

data class ObjBuildUpAreaLimits (
    @SerializedName("buildupArealowerLimit")
    var lowerLimit :String? = null,
    @SerializedName("buildupAreaupperLimit")
    var upperLimit :String? = null
        )
