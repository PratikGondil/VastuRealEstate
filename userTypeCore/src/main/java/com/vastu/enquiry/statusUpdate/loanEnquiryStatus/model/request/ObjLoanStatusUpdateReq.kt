package com.vastu.enquiry.statusUpdate.loanEnquiryStatus.model.request

import com.google.gson.annotations.SerializedName

data class ObjLoanStatusUpdateReq(
    @SerializedName("emp_id") var emp_id : String? = null,
    @SerializedName("loan_enq_id") var loan_enq_id : String? = null,
    @SerializedName("status") var status :String? = null
)
