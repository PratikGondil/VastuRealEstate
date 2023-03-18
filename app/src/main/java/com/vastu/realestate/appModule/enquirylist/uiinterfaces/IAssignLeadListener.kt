package com.vastu.realestate.appModule.enquirylist.uiinterfaces

import com.vastu.enquiry.loan.model.response.LoanData
import com.vastu.enquiry.loan.model.response.getAssignedLoanEnquiry.ObjEmpEnquiryDetailsData
import com.vastu.enquiry.property.model.response.EnquiryData
import com.vastu.enquiry.property.model.response.getAssignedPropertyEnquiry.ObjEmpPropertyEnquiryDtlsData

interface IAssignLeadListener {
    fun assignLoanLeadToEmployee(loanData: LoanData)
    fun assignPropertyLeadToEmployee(PropertyData: EnquiryData)
    fun onAssignLeadSuccess()
    fun onEmpListFailure(message: String,isSuccess:Boolean,isNetworkFailure:Boolean)
    fun updateLoanLeadStatus(loanData: ObjEmpEnquiryDetailsData)
    fun updatePropertyLeadStatus(PropertyData: ObjEmpPropertyEnquiryDtlsData)

}