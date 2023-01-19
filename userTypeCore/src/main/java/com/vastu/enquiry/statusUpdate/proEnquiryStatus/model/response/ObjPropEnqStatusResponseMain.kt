package com.vastu.enquiry.statusUpdate.proEnquiryStatus.model.response

import com.google.gson.annotations.SerializedName

data class ObjPropEnqStatusResponseMain(
    @SerializedName("statusEmpAssignResponse" ) var statusEmpAssignResponse : ObjStatusEmpAssignResponse? = ObjStatusEmpAssignResponse()

)
