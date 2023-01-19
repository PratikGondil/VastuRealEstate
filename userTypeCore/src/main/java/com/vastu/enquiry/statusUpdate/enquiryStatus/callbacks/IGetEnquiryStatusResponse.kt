package com.vastu.enquiry.statusUpdate.enquiryStatus.callbacks

import com.vastu.enquiry.statusUpdate.enquiryStatus.model.response.ObjEnquiryStatusResponseMain

interface IGetEnquiryStatusResponse {
    fun onGetEnquirySuccessResponse(objEnquiryStatusResponseMain:ObjEnquiryStatusResponseMain)
    fun onGetEnquiryFailure(objEnquiryStatusResponseMain:ObjEnquiryStatusResponseMain)
}