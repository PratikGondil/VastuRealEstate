package com.vastu.enquiry.property.model.request.assignEnquiry

import com.google.gson.annotations.SerializedName

data class ObjAssignPropertyEnquiryReq(
    @SerializedName("property_enq_id")
    var property_enq_id:String?=null,
    @SerializedName("emp_id")
    var emp_id:String?=null
)
