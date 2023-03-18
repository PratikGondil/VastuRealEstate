package com.vastu.enquiry.employee.model.employeeDetails

import com.google.gson.annotations.SerializedName

data class ObjEmpDetailsResponseMain(
    @SerializedName("EmployeeDetailsResponse"    ) var employeeDetailsResponse    : ObjEmpDetailsResponse?    = ObjEmpDetailsResponse(),
    @SerializedName("GetEmployeeDetailsResponse" ) var GetEmployeeDetailsResponse : ObjGetEmpDetailsResponse? = ObjGetEmpDetailsResponse()
)