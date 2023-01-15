package com.vastu.enquiry.employee.model.response

import com.google.gson.annotations.SerializedName

data class ObjEmployeeListResponse(
    @SerializedName("EmployeeResponse"    ) var objEmployeeResponse    : ObjEmployeeResponse?,
    @SerializedName("GetEmployeeResponse" ) var objGetEmployeeResponse : ObjGetEmployeeResponse? = ObjGetEmployeeResponse()

)
