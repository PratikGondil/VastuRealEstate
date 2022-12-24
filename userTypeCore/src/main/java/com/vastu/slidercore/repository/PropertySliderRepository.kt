package com.vastu.slidercore.repository

import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.realestate.commoncore.utils.ErrorCode
import com.vastu.slidercore.callback.request.IGetPropertySliderByIdRequest
import com.vastu.slidercore.callback.response.IGetPropertySliderByIdResponse
import com.vastu.slidercore.model.request.PropertySliderRequest
import com.vastu.slidercore.model.response.PropertySliderResponseMain

object PropertySliderRepository:IGetPropertySliderByIdRequest,IOnServiceResponseListener {

   lateinit var iGetPropertySliderByIdResponse: IGetPropertySliderByIdResponse

    override fun callGetPropertySliderById(
        propertyId: String,
        urlEndPoint: String,
        iGetPropertySliderByIdResponse: IGetPropertySliderByIdResponse
    ) {
        PropertySliderRepository.iGetPropertySliderByIdResponse = iGetPropertySliderByIdResponse
        NetworkDaoBuilder.Builder
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
        when(propertySliderResponse.propertySliderImagesResponse.responseStatusHeader.statusCode){
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

    private fun parseResponse(response: String): PropertySliderResponseMain {
        return Gson().fromJson(
            response,
            PropertySliderResponseMain::class.java
        )
    }
}