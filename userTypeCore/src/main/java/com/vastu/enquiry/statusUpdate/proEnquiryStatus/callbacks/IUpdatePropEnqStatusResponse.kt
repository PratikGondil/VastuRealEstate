package com.vastu.enquiry.statusUpdate.proEnquiryStatus.callbacks

import com.vastu.enquiry.statusUpdate.proEnquiryStatus.model.response.ObjPropEnqStatusResponseMain

interface IUpdatePropEnqStatusResponse {
    fun onUpdatePropEnqStatusSuccess(objPropEnqStatusResponseMain: ObjPropEnqStatusResponseMain)
    fun onUpdatePropEnqStatusFailure(objPropEnqStatusResponseMain: ObjPropEnqStatusResponseMain)
}