package com.vastu.realestate.logincore.model.response

import com.google.gson.annotations.SerializedName

data class ObjLoginResponseMain(
    @SerializedName ("LoginResponse") var objLoginResponse :ObjLoginResponse,
    @SerializedName("LoginDetails") var objLoginDtls :ObjLoginDtls
)
