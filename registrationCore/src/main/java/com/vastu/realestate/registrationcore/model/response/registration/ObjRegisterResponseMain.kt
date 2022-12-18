package com.vastu.realestate.registrationcore.model.response.registration

import com.google.gson.annotations.SerializedName
import com.vastu.realestate.registrationcore.model.response.registration.ObjRegisterDlts
import com.vastu.realestate.registrationcore.model.response.registration.ObjRegisterResponse

data class ObjRegisterResponseMain(
    @SerializedName ("RegisterResponse") var objRegisterResponse : ObjRegisterResponse,
    @SerializedName("RegisterDetails")  var objRegisterDlts: ObjRegisterDlts
)
