package com.vastu.realestate.appModule.employee.uiListener

import com.vastu.enquiry.employee.model.response.ObjEmployeeData
import com.vastu.enquiry.employee.model.response.ObjEmployeeResponse

interface IEmpListViewListener {
    fun onEmpSelected(objEmployeeData: ObjEmployeeData)
    fun onEmpListFailure(objEmployeeResponse: ObjEmployeeResponse)

}