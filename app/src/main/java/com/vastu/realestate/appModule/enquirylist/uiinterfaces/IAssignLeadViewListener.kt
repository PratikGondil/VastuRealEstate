package com.vastu.realestate.appModule.enquirylist.uiinterfaces

import com.vastu.enquiry.employee.model.response.ObjEmployeeResponse

interface IAssignLeadViewListener {
    fun callAssignApi()
    fun onEmpListFailure(errorResponse: String)
    fun onLeadAssignSuccess()
    fun updateEnquiryStatusSuccess()
    fun onGetEnquirySuccessResponse()

}