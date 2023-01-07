package com.vastu.realestatecore.model.filter

import com.google.gson.annotations.SerializedName

data class ObjFilterTypeData(
    @SerializedName("filtertypelist") var objFilterTypeList: ArrayList<com.vastu.realestatecore.model.filter.ObjFilterTypeList>
)
