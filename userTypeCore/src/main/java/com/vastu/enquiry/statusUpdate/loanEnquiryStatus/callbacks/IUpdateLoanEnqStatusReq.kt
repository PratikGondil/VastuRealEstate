package com.vastu.enquiry.statusUpdate.loanEnquiryStatus.callbacks

import android.content.Context
import com.vastu.enquiry.statusUpdate.loanEnquiryStatus.model.request.ObjLoanStatusUpdateReq
import com.vastu.enquiry.statusUpdate.loanEnquiryStatus.model.response.ObjLoanEnqStatusResponseMain

interface IUpdateLoanEnqStatusReq {
    fun updateLoanEnqStatus(context: Context,objLoanStatusUpdateReq: ObjLoanStatusUpdateReq,urlEndPoint:String,iUpdateLoanEnqStatusResposne: IUpdateLoanEnqStatusResposne)
}