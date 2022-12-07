package com.vastu.realestate.logincore.model.response

import com.google.gson.annotations.SerializedName

data class ObjLoginDtls(
    @SerializedName ("user_id") var userId:String,
    @SerializedName("first_name") var firstName:String,
    @SerializedName("mobile") var mobile:String,
    @SerializedName("email") var eamilId:String
)
