package com.vastu.enquiry.employee.model.response.employeeDetails

import com.google.gson.annotations.SerializedName

data class ObjEmpDetailsResponseMain(
    @SerializedName("EmployeeDetailsResponse"    ) var employeeDetailsResponse    : ObjEmpDetailsResponse?    = ObjEmpDetailsResponse(),
    @SerializedName("GetEmployeeDetailsResponse" ) var GetEmployeeDetailsResponse : ObjGetEmpDetailsResponse? = ObjGetEmpDetailsResponse()
)