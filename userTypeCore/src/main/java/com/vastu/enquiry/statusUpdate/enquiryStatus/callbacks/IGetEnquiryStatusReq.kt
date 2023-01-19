package com.vastu.enquiry.statusUpdate.enquiryStatus.callbacks

import android.content.Context
import com.vastu.enquiry.loan.callback.assignEnquiry.response.IAssignLeadResponse
import com.vastu.enquiry.property.model.request.assignEnquiry.ObjAssignPropertyEnquiryReq

interface IGetEnquiryStatusReq {
    fun getEnquiryStatusList(context: Context, urlEndPoint: String, iGetEnquiryStatusResponse: IGetEnquiryStatusResponse
    )
}