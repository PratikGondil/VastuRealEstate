package com.vastu.realCreator.rate_us.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.realCreator.rate_us.callback.IGetRatUsCreatorResListener
import com.vastu.realCreator.rate_us.callback.IGetRateUsReq
import com.vastu.realCreator.rate_us.model.CreatorRateUsRes
import com.vastu.realCreator.rate_us.model.ObjCreatorRateUsReq
import com.vastu.utils.ErrorCode

object CreatorRateUsRepository:  IGetRateUsReq, IOnServiceResponseListener {

    private fun buildRequest(request: String, language: String,realCreatorId: String): ByteArray {
        val objCreatorRateUsReq = ObjCreatorRateUsReq(language, request,realCreatorId)
        return Gson().toJson(objCreatorRateUsReq).toByteArray()
    }

    lateinit var iGetRatUsCreatorResListener: IGetRatUsCreatorResListener


    override fun onSuccessResponse(response: String, isError: Boolean) {
        var detailsResponse = parseResponse(response)
        when (detailsResponse.rateUsResponse.responseStatusHeader.statusCode){
            ErrorCode.success ->
                iGetRatUsCreatorResListener.getRealCreatorRateUsSuccessResponse(detailsResponse)
            ErrorCode.error_0001 ->
                iGetRatUsCreatorResListener.getRealCreatorRateUsFailureResponse(detailsResponse)
            else ->
                iGetRatUsCreatorResListener.getRealCreatorRateUsFailureResponse(detailsResponse)

        }

    }

    private fun parseResponse(response: String): CreatorRateUsRes {
        return Gson().fromJson(
            response,
            CreatorRateUsRes::class.java
        )
    }

    override fun onFailureResponse(response: String) {
        iGetRatUsCreatorResListener.getRealCreatorRateUsFailureResponse(parseResponse(response))
    }

    override fun onUserNotConnected() {
        iGetRatUsCreatorResListener.networkFailure()
    }

    override fun callGetRateUs(
        context: Context,
        userId: String,
        rateUs: String,
        realCreatorId: String,
        urlEndPoint: String,
        iGetRatUsCreatorResListener: IGetRatUsCreatorResListener
    ) {
        this.iGetRatUsCreatorResListener=iGetRatUsCreatorResListener
        NetworkDaoBuilder.Builder
            .setContext(context)
            .setIsContentTypeJSON(true)
            .setIsRequestPost(true)
            .setRequest(buildRequest(userId, rateUs,realCreatorId))
            .setUrlEndPoint(urlEndPoint)
            .build()
            .sendApiRequest(this)
    }


}