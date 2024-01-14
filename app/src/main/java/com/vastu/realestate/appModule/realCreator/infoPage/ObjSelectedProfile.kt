package com.vastu.realestate.appModule.realCreator.infoPage

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ObjSelectedProfile
    (

    @SerializedName("profile") var profile:String?=null,
    @SerializedName("taluka") var taluka :String? = null,
    @SerializedName("sub_area") var subArea :String? = null

            ):Serializable