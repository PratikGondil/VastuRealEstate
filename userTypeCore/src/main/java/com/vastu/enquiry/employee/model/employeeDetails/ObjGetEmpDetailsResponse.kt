package com.vastu.enquiry.employee.model.employeeDetails

import com.google.gson.annotations.SerializedName

data class ObjGetEmpDetailsResponse (
    @SerializedName("EmployeeDetailsData" ) var EmployeeDetailsData : ArrayList<ObjEmpDetailsData> = arrayListOf()

)
