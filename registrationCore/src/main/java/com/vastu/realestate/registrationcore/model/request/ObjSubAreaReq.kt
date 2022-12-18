package com.vastu.realestate.registrationcore.model.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ObjSubAreaReq(
    @SerializedName("taluka_id") var talukaId:String?=null
):Serializable
