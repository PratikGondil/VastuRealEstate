package com.vastu.enquiry.employee.model.response.employeeDetails.request

import com.google.gson.annotations.SerializedName

data class ObjEmpDetailRequest(
    @SerializedName("emp_id") var empId :String
)
