package com.vastu.usertypecore.repository

import com.vastu.usertypecore.callbacks.request.IGetUserTypeReq
import com.vastu.usertypecore.callbacks.response.IGetUserTypeResListener
import com.vastu.usertypecore.model.request.ObjGetUserTypeReq
import com.vastu.usertypecore.model.response.ObjGetUserTypeResMain
import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.realestate.commoncore.utils.ErrorCode

object UserTypeRepository: IGetUserTypeReq,IOnServiceResponseListener {
    lateinit var iGetUserTypeResListener: IGetUserTypeResListener
    override fun callGetUserType(
        userId: String,
        urlEndPoint: String,
        iGetUserTypeResListener: IGetUserTypeResListener
    ) {
      this.iGetUserTypeResListener = iGetUserTypeResListener
        NetworkDaoBuilder.Builder
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(buildRequest(userId))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }
    private fun buildRequest(request: String): ByteArray {
        var objLoginReq = ObjGetUserTypeReq(request)
        return Gson().toJson(objLoginReq).toByteArray()
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
       val userTypeResponse = parseResponse(response)
        when(userTypeResponse.userTypeResponse.responseStatusHeader.statusCode){
            ErrorCode.success->
                iGetUserTypeResListener.getUserTypeSuccessResponse(userTypeResponse)
            ErrorCode.error_0001->
                iGetUserTypeResListener.getUserTypeFailureResponse(userTypeResponse)
            else ->
                iGetUserTypeResListener.getUserTypeFailureResponse(userTypeResponse)
        }
    }

    override fun onFailureResponse(response: String) {
        iGetUserTypeResListener.getUserTypeFailureResponse(parseResponse(response))
    }

    override fun onUserNotConnected() {
       iGetUserTypeResListener.networkFailure()
    }

    private fun parseResponse(response: String): ObjGetUserTypeResMain {
        return Gson().fromJson(
            response,
            ObjGetUserTypeResMain::class.java
        )
    }
}