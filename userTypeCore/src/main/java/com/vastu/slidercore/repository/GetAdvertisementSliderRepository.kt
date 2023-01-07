package com.vastu.slidercore.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.slidercore.callback.request.IGetAdvertisementSliderRequest
import com.vastu.slidercore.callback.response.IGetAdvertisementResponseListener
import com.vastu.slidercore.model.response.advertisement.GetAdvertisementSliderMainResponse
import com.vastu.utils.ErrorCode

object GetAdvertisementSliderRepository :IGetAdvertisementSliderRequest,IOnServiceResponseListener {

    private lateinit var iGetAdvertisementResponseListener: IGetAdvertisementResponseListener

    override fun callGetAdvertisementSlider(
        context: Context,
        urlEndPoint: String,
        iGetAdvertisementResponseListener: IGetAdvertisementResponseListener
    ) {
        this.iGetAdvertisementResponseListener = iGetAdvertisementResponseListener
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(false)
            .setIsRequestPut(false)
            .setRequest(HashMap<String, String>())
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
        val advertisementSliderResponse = parseAdvertisementSliderResponse(response)
        when(advertisementSliderResponse.advertiseResponse.responseStatusHeader.statusCode){
            ErrorCode.success->
                iGetAdvertisementResponseListener.onGetAdvertisementSuccessResponse(advertisementSliderResponse)
            ErrorCode.error_0001->
                iGetAdvertisementResponseListener.onGetAdvertisementFailureResponse(advertisementSliderResponse)
            else->
                iGetAdvertisementResponseListener.onGetAdvertisementFailureResponse(advertisementSliderResponse)
        }
    }

    override fun onFailureResponse(response: String) {
        iGetAdvertisementResponseListener.onGetAdvertisementFailureResponse(
            parseAdvertisementSliderResponse(response)
        )
    }

    override fun onUserNotConnected() {
      iGetAdvertisementResponseListener.networkFailure()
    }


    private fun parseAdvertisementSliderResponse(response: String): GetAdvertisementSliderMainResponse {
        return Gson().fromJson(
            response,
           GetAdvertisementSliderMainResponse::class.java
        )
    }
}