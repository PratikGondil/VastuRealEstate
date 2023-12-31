package com.vastu.realestatecore.repository

import android.content.Context
import com.vastu.realestatecore.callback.request.IGetPropertyListReq
import com.vastu.realestatecore.callback.response.IGetPropertyListResListener
import com.vastu.usertypecore.model.request.ObjGetUserTypeReq
import com.vastu.realestatecore.model.response.ObjGetPropertyListResMain
import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.utils.ErrorCode

object PropertyListRepository: IGetPropertyListReq,IOnServiceResponseListener {
    lateinit var iGetPropertyListResListener: IGetPropertyListResListener
    override fun callGetPropertyList(
        context: Context,
        userId: String,
        urlEndPoint: String,
        iGetPropertyListResListener: IGetPropertyListResListener
    ) {
      this.iGetPropertyListResListener = iGetPropertyListResListener
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(buildRequest(userId))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }
    private fun buildRequest(request: String): ByteArray {
        val objLoginReq = ObjGetUserTypeReq(request)
        return Gson().toJson(objLoginReq).toByteArray()
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
       val propertyListResponse = parseResponse(response)
        when(propertyListResponse.propertyResponse.responseStatusHeader.statusCode){
           ErrorCode.success ->
               iGetPropertyListResListener.getPropertyListSuccessResponse(propertyListResponse)
            ErrorCode.error_0001->
                iGetPropertyListResListener.getPropertyListFailureResponse(propertyListResponse)
            else->
                iGetPropertyListResListener.getPropertyListFailureResponse(propertyListResponse)
        }
    }

    override fun onFailureResponse(response: String) {
      iGetPropertyListResListener.getPropertyListFailureResponse(parseResponse( response))
    }

    override fun onUserNotConnected() {
       iGetPropertyListResListener.networkFailure()
    }

    private fun parseResponse(response: String): ObjGetPropertyListResMain {
        return Gson().fromJson(
            response,
            ObjGetPropertyListResMain::class.java
        )
    }
}