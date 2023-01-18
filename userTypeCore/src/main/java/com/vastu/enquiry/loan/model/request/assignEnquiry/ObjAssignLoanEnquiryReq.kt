package com.vastu.enquiry.loan.model.request.assignEnquiry

import com.google.gson.annotations.SerializedName

data class ObjAssignLoanEnquiryReq(
    @SerializedName("loan_enq_id")
    var loan_enq_id:String?=null,
    @SerializedName("emp_id")
    var emp_id:String?=null
)
