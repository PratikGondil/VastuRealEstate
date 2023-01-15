package com.vastu.realestate.appModule.employee.uiListener

import com.vastu.enquiry.employee.model.response.employeeDetails.ObjEmpDetailsResponse
import com.vastu.enquiry.employee.model.response.employeeDetails.ObjEmpDetailsResponseMain

interface IEmpDetailsViewListener {
    fun initView()
    fun getBundleData()
    fun callEmployeeDetails()
    fun onEmployeeSuccessResponse(objEmpDetailsResponseMain: ObjEmpDetailsResponseMain)
    fun onEmployeeFailureResponse(employeeDetailsResponse: ObjEmpDetailsResponse?)
}