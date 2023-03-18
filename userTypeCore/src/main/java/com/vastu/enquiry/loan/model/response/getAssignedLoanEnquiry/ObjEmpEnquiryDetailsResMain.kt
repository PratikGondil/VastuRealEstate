package com.vastu.enquiry.loan.model.response.getAssignedLoanEnquiry

import com.google.gson.annotations.SerializedName

data class ObjEmpEnquiryDetailsResMain(
    @SerializedName("EmployeeEnquiryDetailsResponse"    ) var objEmpEnquiryDetailsResponse    : ObjEmpEnquiryDetailsRes?    = ObjEmpEnquiryDetailsRes(),
    @SerializedName("GetEmployeeEnquiryDetailsResponse" ) var getEmployeeEnquiryDetailsResponse : ObjGetEmpEnquiryDetailsRes? = ObjGetEmpEnquiryDetailsRes()
)