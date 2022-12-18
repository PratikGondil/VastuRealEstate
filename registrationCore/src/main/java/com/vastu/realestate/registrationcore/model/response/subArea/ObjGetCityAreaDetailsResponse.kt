package com.vastu.realestate.registrationcore.model.response.subArea

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ObjGetCityAreaDetailsResponse (
    @SerializedName("CityAreaData") var objCityAreaData :List<ObjCityAreaData>
):Serializable
