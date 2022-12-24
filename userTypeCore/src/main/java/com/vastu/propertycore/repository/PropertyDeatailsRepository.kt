package com.vastu.propertycore.repository

import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.propertycore.callback.request.IGetPropertyDetailsRequest
import com.vastu.propertycore.callback.request.response.IGetPropertyDetailsResponseListener
import com.vastu.propertycore.model.request.GetPropertyRequest
import com.vastu.propertycore.model.response.PropertyDataResponseMain
import com.vastu.realestate.commoncore.utils.ErrorCode

object PropertyDetailsRepository :IGetPropertyDetailsRequest,IOnServiceResponseListener{
    private lateinit var iGetPropertyDetailsResponseListener: IGetPropertyDetailsResponseListener
    override fun callGetPropertyDetails(
        userId: String,
        propertyId: String,
        urlEndPoint: String,
        iGetPropertyDetailsResponseListener: IGetPropertyDetailsResponseListener
    ) {
      PropertyDetailsRepository.iGetPropertyDetailsResponseListener = iGetPropertyDetailsResponseListener;
        NetworkDaoBuilder.Builder
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
    private fun parseResponse(response: String): PropertyDataResponseMain {
        return Gson().fromJson(
            response,
            PropertyDataResponseMain::class.java
        )
    }
}