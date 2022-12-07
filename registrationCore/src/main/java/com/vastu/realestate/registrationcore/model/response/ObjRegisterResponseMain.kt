package com.vastu.realestate.registrationcore.model.response

import com.google.gson.annotations.SerializedName

data class ObjRegisterResponseMain(
    @SerializedName ("RegisterResponse") var objRegisterResponse :ObjRegisterResponse,
    @SerializedName("RegisterDetails")  var objRegisterDlts:ObjRegisterDlts
)
