package com.vastu.realestate.commoncore.model.otp.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ObjVerifyDtls (
    @SerializedName ("user_id") var userId : String?=null,
    @SerializedName ("first_name") var firstName : String?=null,
    @SerializedName ("mobile") var mobileNo: String?=null,
    @SerializedName ("email") var emailId :String?=null,
    @SerializedName("city") var city :String?=null
        ): Serializable
