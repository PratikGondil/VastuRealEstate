package com.vastu.enquiry.employee.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ObjEmployeeData(
    @SerializedName("emp_id"   ) var empId   : String? = null,
    @SerializedName("emp_name" ) var empName : String? = null
):Serializable {
    override fun toString(): String {
        return "$empName"
    }
}
