package com.vastu.enquiry.employee.callbacks.request

import android.content.Context
import com.vastu.enquiry.employee.callbacks.response.IGetEmpDetailsResponse

interface IEmpDetailsRequest {
    fun getEmpDetails(context: Context, empId: String,urlEndPoint:String,iGetEmpDetailsResponse: IGetEmpDetailsResponse)
}