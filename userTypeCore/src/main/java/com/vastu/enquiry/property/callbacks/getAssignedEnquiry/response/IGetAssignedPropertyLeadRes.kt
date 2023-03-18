package com.vastu.enquiry.property.callbacks.getAssignedEnquiry.response

import com.vastu.enquiry.property.model.response.getAssignedPropertyEnquiry.ObjEmpPropertyEnquiryDtlsResMain

interface IGetAssignedPropertyLeadRes {
    fun onSuccessAssignedPropertyLead(objEmpPropertyEnquiryDtlsResMain: ObjEmpPropertyEnquiryDtlsResMain)
    fun onFailureAssignedPropertyLead(objEmpPropertyEnquiryDtlsResMain: ObjEmpPropertyEnquiryDtlsResMain)

}