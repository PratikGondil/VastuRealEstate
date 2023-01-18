package com.vastu.enquiry.loan.callback.assignEnquiry.response

import com.vastu.enquiry.loan.model.response.assignEnquiry.ObjAssignEnquiryReponse

interface IAssignLeadResponse {
    fun onSuccessAssignLoanLead(objAssignEnquiryReponse: ObjAssignEnquiryReponse)
    fun onFailureAssignLoanLead(objAssignEnquiryReponse: ObjAssignEnquiryReponse)
}