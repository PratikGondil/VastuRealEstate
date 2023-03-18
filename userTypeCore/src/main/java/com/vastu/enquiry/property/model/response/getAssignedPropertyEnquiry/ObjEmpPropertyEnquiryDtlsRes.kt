package com.vastu.enquiry.property.model.response.getAssignedPropertyEnquiry

import com.google.gson.annotations.SerializedName
import com.vastu.usertypecore.model.response.ResponseStatusHeader

data class ObjEmpPropertyEnquiryDtlsRes(
@SerializedName("ResponseStatusHeader" ) var ResponseStatusHeader : ResponseStatusHeader? = ResponseStatusHeader()

)
