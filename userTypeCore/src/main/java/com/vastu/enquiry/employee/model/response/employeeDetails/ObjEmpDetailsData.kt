package com.vastu.enquiry.employee.model.response.employeeDetails

import com.google.gson.annotations.SerializedName

data class ObjEmpDetailsData(
    @SerializedName("emp_id"   ) var empId   : String? = null,
    @SerializedName("emp_name" ) var empName : String? = null,
    @SerializedName("sub_area" ) var subArea : String? = null,
    @SerializedName("taluka"   ) var taluka  : String? = null,
    @SerializedName("mobile"   ) var mobile  : String? = null,
    @SerializedName("email"    ) var email   : String? = null,
    @SerializedName("address"  ) var address : String? = null,
    @SerializedName("rating"   ) var rating  : String? = null
)
