package com.vastu.realestate.registrationcore.model.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ObjUserInfo(
    @SerializedName("first_name")var firstName :String?=null,
    @SerializedName("middle_name") var middleName:String? = null,
    @SerializedName("last_name")var lastName:String? = null,
    @SerializedName("mobile") var mobile:String? = null,
    @SerializedName("city")var city :String? = null,
    @SerializedName("sub_area")var subArea :String?=null,
    @SerializedName("email") var emailId :String?=null,
    @SerializedName("user_type") var userType:String?=null,
    @SerializedName("lat") var latitude:String?=null,
    @SerializedName("long") var longitude:String?=null,
    @SerializedName("address") var address:String?=null,
    @SerializedName("address") var language:String?=null
): Serializable