package com.vastu.enquiry.employee.model.response.employeeDetails

import com.google.gson.annotations.SerializedName

data class ObjGetEmpDetailsResponse (
    @SerializedName("EmployeeDetailsData" ) var EmployeeDetailsData : ArrayList<ObjEmpDetailsData> = arrayListOf()

)
