package com.vastu.slidercore.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.slidercore.callback.request.IGetPropertySliderByIdRequest
import com.vastu.slidercore.callback.response.IGetPropertySliderByIdResponse
import com.vastu.slidercore.model.request.PropertySliderRequest
import com.vastu.slidercore.model.response.property.PropertySliderResponseMain
import com.vastu.slidercore.model.response.realestatedetails.PropertyDetailsResponseSliderMain
import com.vastu.utils.ErrorCode

object PropertySliderRepository:IGetPropertySliderByIdRequest,IOnServiceResponseListener {

   lateinit var iGetPropertySliderByIdResponse: IGetPropertySliderByIdResponse

    override fun callGetPropertySliderById(
        context: Context,
        propertyId: String,
        urlEndPoint: String,
        iGetPropertySliderByIdResponse: IGetPropertySliderByIdResponse
    ) {
       this.iGetPropertySliderByIdResponse = iGetPropertySliderByIdResponse
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(buildRequest(propertyId))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }
    private fun buildRequest(request: String): ByteArray {
        val propertySliderRequest = PropertySliderRequest(request)
        return Gson().toJson(propertySliderRequest).toByteArray()
    }


    override fun onSuccessResponse(response: String, isError: Boolean) {
        val propertySliderResponse = parseResponse(response)
        when(propertySliderResponse.PropertySliderImagesResponse.ResponseStatusHeader.statusCode){
            ErrorCode.success->
                iGetPropertySliderByIdResponse.getPropertySliderByIdSuccessResponse(propertySliderResponse)
            ErrorCode.error_0001->
                iGetPropertySliderByIdResponse.getPropertySliderByIdFailureResponse(propertySliderResponse)
            else ->
                iGetPropertySliderByIdResponse.getPropertySliderByIdFailureResponse(propertySliderResponse)
        }
    }

    override fun onFailureResponse(response: String) {
        iGetPropertySliderByIdResponse.getPropertySliderByIdFailureResponse(parseResponse(response))
    }

    override fun onUserNotConnected() {
       iGetPropertySliderByIdResponse.networkFailure()
    }

    private fun parseResponse(response: String): PropertyDetailsResponseSliderMain {
        return Gson().fromJson(
            response,
            PropertyDetailsResponseSliderMain::class.java
        )
    }
}