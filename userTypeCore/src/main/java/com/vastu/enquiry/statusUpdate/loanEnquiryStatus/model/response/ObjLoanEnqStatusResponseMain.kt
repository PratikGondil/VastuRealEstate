package com.vastu.enquiry.statusUpdate.loanEnquiryStatus.model.response

import com.google.gson.annotations.SerializedName

data class ObjLoanEnqStatusResponseMain(
    @SerializedName("statusLoanEmpAssignResponse" ) var statusLoanEmpAssignResponse : ObjStatusLoanEmpAssignRes? = ObjStatusLoanEmpAssignRes()

)
