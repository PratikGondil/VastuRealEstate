package com.vastu.realestate.registrationcore.model.response.subArea

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ObjCityAreaData (
    @SerializedName("area_id") var areaId:String,
    @SerializedName("sub_area")var subArea:String
    ): Serializable {
    override fun toString(): String {
        return "$subArea"
    }
}