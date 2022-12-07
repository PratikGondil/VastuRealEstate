package com.vastu.realestate.commoncore.model.otp.response

import com.google.gson.annotations.SerializedName

data class ObjVerifyDtls (
    @SerializedName ("user_id") var userId : String,
    @SerializedName ("first_name") var firstName : String,
    @SerializedName ("mobile") var MobileNo: String,
    @SerializedName ("email") var emailId :String
        )
