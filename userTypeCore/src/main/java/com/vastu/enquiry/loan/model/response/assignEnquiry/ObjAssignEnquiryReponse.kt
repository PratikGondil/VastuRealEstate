package com.vastu.enquiry.loan.model.response.assignEnquiry

import com.google.gson.annotations.SerializedName

data class ObjAssignEnquiryReponse(
    @SerializedName("PropertyEmpAssignResponse" ) var objPropertyEmpAssignResponse : ObjPropertyEmpAssignResponse? = ObjPropertyEmpAssignResponse()
)
