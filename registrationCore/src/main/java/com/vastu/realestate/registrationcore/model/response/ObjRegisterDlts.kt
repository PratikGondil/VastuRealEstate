package com.vastu.realestate.registrationcore.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ObjRegisterDlts(
@SerializedName ("user_id") var userId :String?=null
):Serializable
