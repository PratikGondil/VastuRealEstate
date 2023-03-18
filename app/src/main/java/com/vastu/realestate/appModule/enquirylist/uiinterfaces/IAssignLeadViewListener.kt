package com.vastu.realestate.appModule.enquirylist.uiinterfaces

import com.vastu.enquiry.employee.model.response.ObjEmployeeResponse
import com.vastu.enquiry.statusUpdate.enquiryStatus.model.response.ObjEnquiryStatusResponseMain

interface IAssignLeadViewListener {
    fun proceedToNext()
    fun callAssignApi()
    fun onEmpListFailure(errorResponse: String)
    fun onLeadAssignSuccess()
    fun updateEnquiryStatusSuccess()
    fun onGetEnquirySuccessResponse(objEnquiryStatusResponseMain: ObjEnquiryStatusResponseMain)
    fun closeAssignLead()
}