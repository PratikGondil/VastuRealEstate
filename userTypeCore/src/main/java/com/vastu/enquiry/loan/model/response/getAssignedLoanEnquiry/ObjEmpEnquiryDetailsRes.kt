package com.vastu.enquiry.loan.model.response.getAssignedLoanEnquiry

import com.google.gson.annotations.SerializedName
import com.vastu.usertypecore.model.response.ResponseStatusHeader

data class ObjEmpEnquiryDetailsRes (
    @SerializedName("ResponseStatusHeader" ) var objResponseStatusHeader : ResponseStatusHeader? = ResponseStatusHeader()
        )
