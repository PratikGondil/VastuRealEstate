package com.vastu.enquiry.employee.model.employeeDetails.request

import com.google.gson.annotations.SerializedName

data class ObjEmpDetailRequest(
    @SerializedName("emp_id") var empId :String
)
