package com.vastu.realestate.appModule.rateUs

import android.content.Context
import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.realestate.commoncore.utils.ErrorCode

object RateUsRepository : IRateUsRequest, IOnServiceResponseListener {
    private lateinit var iRateUsResponseListener: IRateUsResponseListener

    override fun callRateUsApi(
        context: Context,
        userId: String,
        rateUs: String,
        feedback: String,
        urlEndPoint: String,
        iRateUsResponseListener: IRateUsResponseListener
    ) {
        this.iRateUsResponseListener = iRateUsResponseListener
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(buildRequest(userId, rateUs, feedback))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }

    fun buildRequest(userId: String, rateUs: String, feedback: String): ByteArray {
        var objRateUsReq = GetRateUsRequest(userId, rateUs, feedback)
        return Gson().toJson(objRateUsReq).toByteArray()
    }

    override fun onSuccessResponse(response: String, isError: Boolean) {
        var rateUsRes = parseResponse(response)
        when (rateUsRes.objRateUsResponse.objResponseStatusHdr.statusCode) {
            ErrorCode.success ->
                iRateUsResponseListener.onGetSuccessResponse(rateUsRes)
            ErrorCode.error_0002 ->
                iRateUsResponseListener.onGetFailureResponse(rateUsRes)
            else ->
                iRateUsResponseListener.onGetFailureResponse(rateUsRes)
        }
    }

    override fun onFailureResponse(response: String) {
        iRateUsResponseListener.onGetFailureResponse(parseResponse(response))
    }

    override fun onUserNotConnected() {
       iRateUsResponseListener.networkFailure()
    }

    private fun parseResponse(response: String): RateUsDataResponseMain {
        return Gson().fromJson(
            response,
            RateUsDataResponseMain::class.java
        )
    }
}