package com.vastu.enquiry.employee.model.employeeDetails

import com.google.gson.annotations.SerializedName
import com.vastu.usertypecore.model.response.ResponseStatusHeader

data class ObjEmpDetailsResponse(
    @SerializedName("ResponseStatusHeader" ) var ResponseStatusHeader : ResponseStatusHeader? = ResponseStatusHeader()

)
