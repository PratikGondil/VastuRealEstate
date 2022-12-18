package com.vastu.realestate.registrationcore.model.response.cityList

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ObjTalukaDataList(
    @SerializedName ("taluka_id") var talukaId:String?=null,
    @SerializedName ("taluka") var taluka :String?= null
): Serializable {
    override fun toString(): String {
        return "$taluka"
    }
}
