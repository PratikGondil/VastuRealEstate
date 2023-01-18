package com.vastu.enquiry.loan.model.response.assignEnquiry

import com.google.gson.annotations.SerializedName
import com.vastu.usertypecore.model.response.ResponseStatusHeader

data class ObjPropertyEmpAssignResponse (
    @SerializedName("ResponseStatusHeader" ) var objResponseStatusHeader : ResponseStatusHeader? = ResponseStatusHeader()

)
