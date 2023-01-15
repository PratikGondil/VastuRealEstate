package com.vastu.enquiry.employee.callbacks.response

import com.vastu.enquiry.employee.model.response.ObjEmployeeListResponse
import com.vastu.loanenquirycore.model.response.enquiry.EnquiryMainResponse

interface IGetEmpListResponse {
    fun onEmpListSuccessResponse(objEmployeeListResponse: ObjEmployeeListResponse)
    fun onEmpListFailureResponse(objEmployeeListResponse: ObjEmployeeListResponse)
    fun networkFailure()
}