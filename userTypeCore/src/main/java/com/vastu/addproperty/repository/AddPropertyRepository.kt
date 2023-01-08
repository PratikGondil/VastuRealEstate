package com.vastu.addproperty.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.addproperty.callback.request.IAddPropertyRequest
import com.vastu.addproperty.callback.response.IAddPropertyResponseListener
import com.vastu.addproperty.model.request.AddPropertyRequest
import com.vastu.addproperty.model.response.AddPropertyMainResponse
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.utils.ErrorCode

object AddPropertyRepository :IOnServiceResponseListener,IAddPropertyRequest{

    private lateinit var iAddPropertyResponseListener: IAddPropertyResponseListener

    override fun callAddProperty(
        context: Context,
        urlEndPoint: String,
        addPropertyRequest: AddPropertyRequest,
        iAddPropertyResponseListener: IAddPropertyResponseListener
    ) {
        this.iAddPropertyResponseListener = iAddPropertyResponseListener
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(buildRequest(addPropertyRequest))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }

    private fun buildRequest(addPropertyRequest: AddPropertyRequest): ByteArray {
        return Gson().toJson(addPropertyRequest).toByteArray()
    }
    override fun onSuccessResponse(response: String, isError: Boolean) {
     val addPropertyRes = parseResponse(response)
     when(addPropertyRes.registerResponse.responseStatusHeader.statusCode){
         ErrorCode.success->
             iAddPropertyResponseListener.onAddPropertySuccess(addPropertyRes)
         ErrorCode.error_0001->
             iAddPropertyResponseListener.onAddPropertyFailure(addPropertyRes)
         else->
             iAddPropertyResponseListener.onAddPropertyFailure(addPropertyRes)
     }
    }

    override fun onFailureResponse(response: String) {
     iAddPropertyResponseListener.onAddPropertyFailure(parseResponse(response))
    }

    override fun onUserNotConnected() {
       iAddPropertyResponseListener.networkFailure()
    }
    private fun parseResponse(response: String): AddPropertyMainResponse {
        return Gson().fromJson(
            response,
            AddPropertyMainResponse::class.java
        )
    }



}