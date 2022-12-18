package com.vastu.realestate.registrationcore.model.response.subArea

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ObjResponseStatusHdr(
    @SerializedName("statusDescription") var statusDescription :String?,
    @SerializedName("statusCode") var statusCode:String?,
    @SerializedName("CityAreaData") var cityAreaData:String
):Serializable