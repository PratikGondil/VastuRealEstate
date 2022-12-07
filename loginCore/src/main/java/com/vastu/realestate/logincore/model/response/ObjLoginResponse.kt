package com.vastu.realestate.logincore.model.response

import com.google.gson.annotations.SerializedName
import com.vastu.realestate.commoncore.model.otp.response.ObjResponseStatusHdr

data class ObjLoginResponse (
    @SerializedName("ResponseStatusHeader") var objResponseStatusHdr: ObjResponseStatusHdr


        )
