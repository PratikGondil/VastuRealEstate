package com.vastu.enquiry.getAssignedEnquiry.request

import com.google.gson.annotations.SerializedName

data class ObjGetAssignedEnquiryReq(
    @SerializedName("emp_id") var empId :String?=""

)
