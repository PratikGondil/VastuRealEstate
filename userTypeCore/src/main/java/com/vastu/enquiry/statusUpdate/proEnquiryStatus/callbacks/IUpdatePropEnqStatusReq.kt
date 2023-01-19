package com.vastu.enquiry.statusUpdate.proEnquiryStatus.callbacks

import android.content.Context
import com.vastu.enquiry.statusUpdate.loanEnquiryStatus.model.response.ObjLoanEnqStatusResponseMain
import com.vastu.enquiry.statusUpdate.proEnquiryStatus.model.request.ObjPropStatusUpdateReq
import com.vastu.enquiry.statusUpdate.proEnquiryStatus.model.response.ObjPropEnqStatusResponseMain
import com.vastu.enquiry.statusUpdate.proEnquiryStatus.repository.UpdatePropertyEnquiryStatus

interface IUpdatePropEnqStatusReq {
    fun updatePropEnqStatus(context: Context, objPropStatusUpdateReq: ObjPropStatusUpdateReq, urlEndPoint:String,iUpdatePropEnqStatusResponse:IUpdatePropEnqStatusResponse)

}