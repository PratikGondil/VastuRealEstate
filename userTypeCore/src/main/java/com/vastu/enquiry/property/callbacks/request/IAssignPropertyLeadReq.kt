package com.vastu.enquiry.property.callbacks.request

import android.content.Context
import com.vastu.enquiry.loan.callback.assignEnquiry.response.IAssignLeadResponse
import com.vastu.enquiry.loan.model.request.assignEnquiry.ObjAssignLoanEnquiryReq
import com.vastu.enquiry.property.model.request.assignEnquiry.ObjAssignPropertyEnquiryReq

interface IAssignPropertyLeadReq {
    fun assignPropertyLead(context: Context, urlEndPoint: String, objAssignPropertyEnquiryReq: ObjAssignPropertyEnquiryReq, iAssignLeadResponse: IAssignLeadResponse
    )
}