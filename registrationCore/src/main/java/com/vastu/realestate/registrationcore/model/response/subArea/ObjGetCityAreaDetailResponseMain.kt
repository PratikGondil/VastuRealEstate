package com.vastu.realestate.registrationcore.model.response.subArea

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ObjGetCityAreaDetailResponseMain (
    @SerializedName("GetCityAreaDetailsResponse") var objGetCityAreaDetailsResponse : ObjGetCityAreaDetailsResponse,
    @SerializedName("CityAreaResponse") var objCityAreaResponse: ObjCityAreaResponse,
):Serializable