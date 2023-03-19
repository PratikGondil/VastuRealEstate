package com.vastu.enquiry.property.model.response.getAssignedPropertyEnquiry

import com.google.gson.annotations.SerializedName

data class ObjGetEmpPropertyEnquiryDtlsResponse(
    @SerializedName("EmployeePropertyEnquiryDetailsData" ) var objEmpPropertyEnquiryDtlsData : ArrayList<ObjEmpPropertyEnquiryDtlsData> = arrayListOf()
)
