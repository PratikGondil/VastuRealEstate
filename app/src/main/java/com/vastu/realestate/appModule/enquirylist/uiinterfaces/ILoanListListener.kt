package com.vastu.realestate.appModule.enquirylist.uiinterfaces

import com.vastu.enquiry.loan.model.response.GetLoanEnquiryListMainResponse
import com.vastu.enquiry.loan.model.response.getAssignedLoanEnquiry.ObjEmpEnquiryDetailsResMain

interface ILoanListListener :INetworkFailListener{
    fun onSuccessGetLoanEnquiry(getLoanEnquiryListMainResponse: GetLoanEnquiryListMainResponse)
    fun onFailureGetLoanEnquiry(getLoanEnquiryListMainResponse: GetLoanEnquiryListMainResponse)
    fun onGetAssignedLoanLeadSuccess(objEmpEnquiryDetailsResMain: ObjEmpEnquiryDetailsResMain)
    fun onFailureGetAssignedLoanEnquiry(objEmpEnquiryDetailsResMain: ObjEmpEnquiryDetailsResMain)
}