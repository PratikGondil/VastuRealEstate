package com.vastu.enquiry.employee.model.response

import com.google.gson.annotations.SerializedName

data class ObjGetEmployeeResponse (
    @SerializedName("EmployeeData" ) var EmployeeData : ArrayList<ObjEmployeeData> = arrayListOf()
        )
