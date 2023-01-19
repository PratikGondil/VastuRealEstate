package com.vastu.enquiry.statusUpdate.loanEnquiryStatus.callbacks

import com.vastu.enquiry.statusUpdate.loanEnquiryStatus.model.response.ObjLoanEnqStatusResponseMain

interface IUpdateLoanEnqStatusResposne {
    fun onUpdateLoanEnqStatusSuccess(objLoanEnqStatusResponseMain: ObjLoanEnqStatusResponseMain)
    fun onUpdateLoanEnqStatusFailure(objLoanEnqStatusResponseMain: ObjLoanEnqStatusResponseMain)

}