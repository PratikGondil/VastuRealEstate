package com.vastu.realestate.registrationcore.model.response.registration

import com.google.gson.annotations.SerializedName
import com.vastu.realestate.commoncore.model.otp.response.ObjResponseStatusHdr

data class ObjRegisterResponse (
    @SerializedName("ResponseStatusHeader") var objResponseStatusHdr: ObjResponseStatusHdr

)
