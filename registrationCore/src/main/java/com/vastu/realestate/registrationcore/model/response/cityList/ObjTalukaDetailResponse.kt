package com.vastu.realestate.registrationcore.model.response.cityList

import com.google.gson.annotations.SerializedName

data class ObjTalukaDetailResponse(
    @SerializedName ("talukaData") var objTalukaDataList:List<ObjTalukaDataList>
)
