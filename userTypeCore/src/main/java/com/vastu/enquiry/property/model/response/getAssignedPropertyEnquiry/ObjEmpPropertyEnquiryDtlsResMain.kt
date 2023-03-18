package com.vastu.enquiry.property.model.response.getAssignedPropertyEnquiry

import com.google.gson.annotations.SerializedName
import com.vastu.enquiry.loan.model.response.getAssignedLoanEnquiry.ObjEmpEnquiryDetailsRes

data class ObjEmpPropertyEnquiryDtlsResMain(
    @SerializedName("EmployeePropertyEnquiryDetailsResponse"    ) var objEmpPropertyEnquiryDtlsRes : ObjEmpPropertyEnquiryDtlsRes?    = ObjEmpPropertyEnquiryDtlsRes(),
    @SerializedName("GetEmployeePropertyEnquiryDetailsResponse") var objGetEmpPropertyEnquiryDtlsResponse : ObjGetEmpPropertyEnquiryDtlsResponse? = ObjGetEmpPropertyEnquiryDtlsResponse()
    )
