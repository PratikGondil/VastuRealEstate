package com.vastu.realestate.registrationcore.model.response.cityList

import com.google.gson.annotations.SerializedName

data class ObjTalukaResponseMain (
    @SerializedName ("talukaResponse")var objTalukaResponse: ObjTalukaResponse,
    @SerializedName("GetTalukaDetailsResponse") var objTalukaDetailResponse : ObjTalukaDetailResponse
    )