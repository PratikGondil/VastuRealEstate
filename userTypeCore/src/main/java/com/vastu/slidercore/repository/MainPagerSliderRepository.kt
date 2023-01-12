package com.vastu.slidercore.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.slidercore.callback.request.IGetMainPageSliderRequest
import com.vastu.slidercore.callback.response.IGetMainPageSliderResponseListener
import com.vastu.slidercore.model.request.MainPagerSliderRequest
import com.vastu.slidercore.model.response.mainpage.MainPageSliderResponse
import com.vastu.utils.ErrorCode

object MainPagerSliderRepository :IOnServiceResponseListener, IGetMainPageSliderRequest {

    private lateinit var iGetMainPageSliderResponseListener: IGetMainPageSliderResponseListener
    override fun callMainPagerSlider(
        context: Context,
        mainPagerSliderRequest: MainPagerSliderRequest,
        urlEndPoint: String,
        iGetMainPageSliderResponseListener: IGetMainPageSliderResponseListener
    ) {
        this.iGetMainPageSliderResponseListener = iGetMainPageSliderResponseListener
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(buildRequest(mainPagerSliderRequest))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }

    private fun buildRequest(mainPagerSliderRequest: MainPagerSliderRequest): ByteArray {
        return Gson().toJson(mainPagerSliderRequest).toByteArray()
    }
   override fun onSuccessResponse(response: String, isError: Boolean) {
       val mainSliderRes = parseResponse(response)
       when(mainSliderRes.mainSliderResponse.responseStatusHeader.statusCode){
           ErrorCode.success->
               iGetMainPageSliderResponseListener.onMainPagerSliderSuccess(mainSliderRes)
           ErrorCode.error_0001->
               iGetMainPageSliderResponseListener.onMainPageSliderFailure(mainSliderRes)
           else->
               iGetMainPageSliderResponseListener.onMainPageSliderFailure(mainSliderRes)
       }
    }

    override fun onFailureResponse(response: String) {
        iGetMainPageSliderResponseListener.onMainPageSliderFailure(parseResponse(response))
    }

    override fun onUserNotConnected() {
        iGetMainPageSliderResponseListener.networkFailure()
    }
    private fun parseResponse(response: String): MainPageSliderResponse {
        return Gson().fromJson(
            response,
            MainPageSliderResponse::class.java
        )
    }
}