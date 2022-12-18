package com.vastu.realestate.registrationcore.model.response.subArea

import com.google.gson.annotations.SerializedName
import com.vastu.realestate.commoncore.model.otp.response.ObjResponseStatusHdr
import java.io.Serializable

data class ObjCityAreaResponse(
    @SerializedName ("ResponseStatusHeader") var objResponseStatusHdr: ObjResponseStatusHdr
    ):Serializable
