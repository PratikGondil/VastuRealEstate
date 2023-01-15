package com.vastu.enquiry.employee.callbacks.request

import android.content.Context
import com.vastu.enquiry.employee.callbacks.response.IGetEmpListResponse

interface IGetEmpListRequest {
    fun getEmployeeList(
        context: Context,
        urlEndPoint: String,
        iGetEmpListResponse: IGetEmpListResponse
    )
}