package com.vastu.enquiry.statusUpdate.proEnquiryStatus.model.response

import com.google.gson.annotations.SerializedName
import com.vastu.usertypecore.model.response.ResponseStatusHeader

data class ObjStatusEmpAssignResponse(
    @SerializedName("ResponseStatusHeader" ) var responseStatusHeader : ResponseStatusHeader? = ResponseStatusHeader())
