package com.vastu.enquiry.loan.callback.assignEnquiry.request

import android.content.Context
import com.vastu.enquiry.loan.callback.assignEnquiry.response.IAssignLeadResponse
import com.vastu.enquiry.loan.model.request.assignEnquiry.ObjAssignLoanEnquiryReq

interface IAssignLoanLeadReq {
    fun assignLoanLead(context: Context, urlEndPoint: String, objAssignLoanEnquiryReq: ObjAssignLoanEnquiryReq, iAssignLoanLeadResponse: IAssignLeadResponse
    )
}