package com.vastu.enquiry.employee.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.enquiry.employee.callbacks.request.IGetEmpListRequest
import com.vastu.enquiry.employee.callbacks.response.IGetEmpListResponse
import com.vastu.enquiry.employee.model.response.ObjEmployeeListResponse
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.utils.ErrorCode

object EmployeeListRepository:IOnServiceResponseListener, IGetEmpListRequest {
    private lateinit var iGetEmpListResponse: IGetEmpListResponse

    override fun getEmployeeList(
        context: Context,
        urlEndPoint: String,
        iGetEmpListResponse: IGetEmpListResponse
    ) {
        this.iGetEmpListResponse = iGetEmpListResponse
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(HashMap<String, String>())
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)

    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
        val empListResponse = parseResponse(response)
        when(empListResponse.objEmployeeResponse?.ResponseStatusHeader?.statusCode){
            ErrorCode.success ->
                iGetEmpListResponse.onEmpListSuccessResponse(empListResponse)
            ErrorCode.error_0001->
                iGetEmpListResponse.onEmpListFailureResponse(empListResponse)
            else->
                iGetEmpListResponse.onEmpListFailureResponse(empListResponse)
        }
    }

    override fun onFailureResponse(response: String) {
        iGetEmpListResponse.onEmpListFailureResponse(parseResponse(response))
    }

    override fun onUserNotConnected() {
        iGetEmpListResponse.networkFailure()
    }

    private fun parseResponse(response: String): ObjEmployeeListResponse {
        return Gson().fromJson(
            response,
            ObjEmployeeListResponse::class.java
        )
    }

}