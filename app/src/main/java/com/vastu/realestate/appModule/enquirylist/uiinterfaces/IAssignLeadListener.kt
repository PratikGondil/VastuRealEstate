package com.vastu.realestate.appModule.enquirylist.uiinterfaces

import com.vastu.enquiry.loan.model.response.LoanData
import com.vastu.enquiry.property.model.response.EnquiryData

interface IAssignLeadListener {
    fun assignLoanLeadToEmployee(loanData: LoanData)
    fun assignPropertyLeadToEmployee(PropertyData: EnquiryData)

    fun onEmpListFailure(message: String,isSuccess:Boolean,isNetworkFailure:Boolean)
}