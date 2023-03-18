package com.vastu.enquiry.loan.callback.getAssignEnquiry.request

import android.content.Context
import com.vastu.enquiry.getAssignedEnquiry.request.ObjGetAssignedEnquiryReq
import com.vastu.enquiry.loan.callback.assignEnquiry.response.IAssignLeadResponse
import com.vastu.enquiry.loan.callback.getAssignEnquiry.response.IGetAssignedLoanLeadRes

interface IGetAssignedLoanEnqReq {
    fun getAssignLoanLead(context: Context, urlEndPoint: String, objGetAssignedEnquiryReq: ObjGetAssignedEnquiryReq, iAssignedLoanLeadResponse: IGetAssignedLoanLeadRes
    )
}