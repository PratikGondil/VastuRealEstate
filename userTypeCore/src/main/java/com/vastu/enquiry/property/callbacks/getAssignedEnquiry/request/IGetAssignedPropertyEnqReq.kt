package com.vastu.enquiry.property.callbacks.getAssignedEnquiry.request

import android.content.Context
import com.vastu.enquiry.getAssignedEnquiry.request.ObjGetAssignedEnquiryReq
import com.vastu.enquiry.property.callbacks.getAssignedEnquiry.response.IGetAssignedPropertyLeadRes

interface IGetAssignedPropertyEnqReq {
    fun getAssignPropertyLead(context: Context, urlEndPoint: String, objGetAssignedEnquiryReq: ObjGetAssignedEnquiryReq, iAssignedPropertyLeadResponse: IGetAssignedPropertyLeadRes
    )
}