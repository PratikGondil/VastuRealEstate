package com.vastu.enquiry.employee.callbacks.response

import com.vastu.enquiry.employee.model.response.employeeDetails.ObjEmpDetailsResponseMain

interface IGetEmpDetailsResponse {
    fun onEmpDetailsSuccessResponse(objEmpDetailsResponseMain: ObjEmpDetailsResponseMain)
    fun onEmpDetailsFailureResponse(objEmpDetailsResponseMain: ObjEmpDetailsResponseMain)
}