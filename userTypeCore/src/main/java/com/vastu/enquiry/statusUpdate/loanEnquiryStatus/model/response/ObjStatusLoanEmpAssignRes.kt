package com.vastu.enquiry.statusUpdate.loanEnquiryStatus.model.response

import com.google.gson.annotations.SerializedName
import com.vastu.usertypecore.model.response.ResponseStatusHeader

data class ObjStatusLoanEmpAssignRes(
    @SerializedName("ResponseStatusHeader" ) var responseStatusHeader : ResponseStatusHeader? = ResponseStatusHeader()

)
