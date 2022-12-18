package com.vastu.realestate.commoncore.model.otp

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ObjUserData(
    var userID :String?=null,
    var mobile:String?=null
):Serializable
