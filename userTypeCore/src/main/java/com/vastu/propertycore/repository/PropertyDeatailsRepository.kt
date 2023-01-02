package com.vastu.propertycore.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.propertycore.callback.request.IGetPropertyDetailsRequest
import com.vastu.propertycore.callback.request.response.IGetPropertyDetailsResponseListener
import com.vastu.propertycore.model.request.GetPropertyRequest
import com.vastu.propertycore.model.response.PropertyDataResponseMain
import com.vastu.utils.ErrorCode

object PropertyDetailsRepository :IGetPropertyDetailsRequest,IOnServiceResponseListener{
    private lateinit var iGetPropertyDetailsResponseListener: IGetPropertyDetailsResponseListener
    override fun callGetPropertyDetails(
        context: Context,
        userId: String,
        propertyId: String,
        urlEndPoint: String,
        iGetPropertyDetailsResponseListener: IGetPropertyDetailsResponseListener
    ) {
      this.iGetPropertyDetailsResponseListener = iGetPropertyDetailsResponseListener;
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(buildRequest(userId,propertyId))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }
    private fun buildRequest(userId: String,propertyId: String): ByteArray {
        val objPropertyReq = GetPropertyRequest(userId,propertyId)
        return Gson().toJson(objPropertyReq).toByteArray()
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
        var propertyDetailsRes = parseResponse(response)
        when(propertyDetailsRes.propertyIdResponse.responseStatusHeader.statusCode){
            ErrorCode.success->
                iGetPropertyDetailsResponseListener.getPropertyDetailsSuccessResponse(propertyDetailsRes)

            ErrorCode.error_0001->
                    iGetPropertyDetailsResponseListener.getPropertyDetailsFailureResponse(propertyDetailsRes)
            else->
                iGetPropertyDetailsResponseListener.getPropertyDetailsFailureResponse(propertyDetailsRes)
        }
    }

    override fun onFailureResponse(response: String) {
        iGetPropertyDetailsResponseListener.getPropertyDetailsFailureResponse(parseResponse(response))
    }

    override fun onUserNotConnected() {
      iGetPropertyDetailsResponseListener.networkFailure()
    }

    private fun parseResponse(response: String): PropertyDataResponseMain {
        return Gson().fromJson(
            response,
            PropertyDataResponseMain::class.java
        )
    }
}