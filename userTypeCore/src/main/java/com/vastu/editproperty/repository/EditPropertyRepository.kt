package com.vastu.editproperty.repository

import android.content.Context
import com.google.gson.Gson

import com.vastu.editproperty.callbacks.request.IEditPropertyRequest
import com.vastu.editproperty.callbacks.response.IEditPropertyResponseListener
import com.vastu.editproperty.model.request.EditPropertyRequest
import com.vastu.editproperty.model.response.EditPropertyMainResponse
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.utils.ErrorCode

object EditPropertyRepository :IOnServiceResponseListener,IEditPropertyRequest{

    private lateinit var iEditPropertyResponseListener: IEditPropertyResponseListener
    override fun callEditProperty(
        context: Context,
        urlEndPoint: String,
        editPropertyRequest: EditPropertyRequest,
        iEditPropertyResponseListener: IEditPropertyResponseListener
    ) {
        this.iEditPropertyResponseListener = iEditPropertyResponseListener
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(buildRequest(editPropertyRequest))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }

    private fun buildRequest(editPropertyRequest: EditPropertyRequest): ByteArray {
        return Gson().toJson(editPropertyRequest).toByteArray()
    }
    override fun onSuccessResponse(response: String, isError: Boolean) {
        val editPropertyResponse = parseResponse(response)
        when(editPropertyResponse.editPropertyResponse.responseStatusHeader.statusCode){
            ErrorCode.success->
                iEditPropertyResponseListener.onSuccessEditProperty(editPropertyResponse)
            ErrorCode.error_0001->
                iEditPropertyResponseListener.onFailureEditProperty(editPropertyResponse)
                else->
                    iEditPropertyResponseListener.onFailureEditProperty(editPropertyResponse)
        }
    }

    override fun onFailureResponse(response: String) {
        iEditPropertyResponseListener.onFailureEditProperty(parseResponse(response))
    }

    override fun onUserNotConnected() {
        iEditPropertyResponseListener.networkFailure()
    }

    private fun parseResponse(response: String): EditPropertyMainResponse {
        return Gson().fromJson(
            response,
            EditPropertyMainResponse::class.java
        )
    }
}