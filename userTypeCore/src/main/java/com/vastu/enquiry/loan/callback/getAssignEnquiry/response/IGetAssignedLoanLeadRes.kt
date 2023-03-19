package com.vastu.enquiry.loan.callback.getAssignEnquiry.response

import com.vastu.enquiry.loan.model.response.assignEnquiry.ObjAssignEnquiryReponse
import com.vastu.enquiry.loan.model.response.getAssignedLoanEnquiry.ObjEmpEnquiryDetailsResMain

interface IGetAssignedLoanLeadRes {
    fun onSuccessAssignedLoanLead(objEmpEnquiryDetailsResMain: ObjEmpEnquiryDetailsResMain)
    fun onFailureAssignedLoanLead(objEmpEnquiryDetailsResMain: ObjEmpEnquiryDetailsResMain)
}