package com.vastu.enquiry.loan.model.response.getAssignedLoanEnquiry

import com.google.gson.annotations.SerializedName

data class ObjGetEmpEnquiryDetailsRes(
    @SerializedName("EmployeeEnquiryDetailsData" ) var objEmpEnquiryDetailsData : List<ObjEmpEnquiryDetailsData> = arrayListOf()

)
