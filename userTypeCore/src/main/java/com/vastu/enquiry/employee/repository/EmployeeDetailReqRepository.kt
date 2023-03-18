package com.vastu.enquiry.employee.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.enquiry.employee.callbacks.request.IEmpDetailsRequest
import com.vastu.enquiry.employee.callbacks.response.IGetEmpDetailsResponse
import com.vastu.enquiry.employee.model.employeeDetails.ObjEmpDetailsResponseMain
import com.vastu.enquiry.employee.model.employeeDetails.request.ObjEmpDetailRequest
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.utils.ErrorCode

object EmployeeDetailReqRepository: IEmpDetailsRequest,IOnServiceResponseListener {
    private lateinit var iGetEmpDetailsResponse: IGetEmpDetailsResponse

    override fun getEmpDetails(
        context: Context,
        empId: String,
        urlEndPoint: String,
        iGetEmpDetailsResponse: IGetEmpDetailsResponse
    ) {
        this.iGetEmpDetailsResponse = iGetEmpDetailsResponse
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(buildRequest(empId))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)

    }
    private fun buildRequest(empId: String): ByteArray {
        val objEmpDetailRequest = ObjEmpDetailRequest(empId)
        return Gson().toJson(objEmpDetailRequest).toByteArray()
    }
    override fun onSuccessResponse(response: String, isError: Boolean) {
        val empDetailsResponse = parseResponse(response)
        when(empDetailsResponse.employeeDetailsResponse?.ResponseStatusHeader?.statusCode){
            ErrorCode.success ->
                iGetEmpDetailsResponse.onEmpDetailsSuccessResponse(empDetailsResponse)
            ErrorCode.error_0001->
                iGetEmpDetailsResponse.onEmpDetailsFailureResponse(empDetailsResponse)
            else->
                iGetEmpDetailsResponse.onEmpDetailsFailureResponse(empDetailsResponse)
        }
    }

    override fun onFailureResponse(response: String) {
        iGetEmpDetailsResponse.onEmpDetailsFailureResponse(parseResponse(response))
    }

    override fun onUserNotConnected() {
//        iGetEmpDetailsResponse.networkFailure()
    }

    private fun parseResponse(response: String): ObjEmpDetailsResponseMain {
        return Gson().fromJson(
            response,
            ObjEmpDetailsResponseMain::class.java
        )
    }

}