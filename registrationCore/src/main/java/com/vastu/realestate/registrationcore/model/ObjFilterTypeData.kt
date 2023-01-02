package com.vastu.realestate.registrationcore.model

import com.google.gson.annotations.SerializedName

data class ObjFilterTypeData(
    @SerializedName("filtertypelist") var objFilterTypeList: ArrayList<ObjFilterTypeList>
)
