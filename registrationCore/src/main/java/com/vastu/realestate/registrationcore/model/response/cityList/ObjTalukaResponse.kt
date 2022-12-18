package com.vastu.realestate.registrationcore.model.response.cityList

import com.google.gson.annotations.SerializedName
import com.vastu.realestate.commoncore.model.otp.response.ObjResponseStatusHdr

data class ObjTalukaResponse (
    @SerializedName("ResponseStatusHeader") var objResponseStatusHdr: ObjResponseStatusHdr

    )
