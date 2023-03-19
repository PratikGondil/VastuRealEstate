package com.vastu.offers.repository

import android.content.Context
import com.google.gson.Gson
import com.vastu.networkService.service.NetworkDaoBuilder
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.offers.callbacks.request.IOffersRequest
import com.vastu.offers.callbacks.response.IGetOffersResponseListener
import com.vastu.offers.model.response.OffersMainResponse
import com.vastu.utils.ErrorCode

object GetOffersRepository :IOnServiceResponseListener,IOffersRequest{
   private lateinit var iGetOffersResponseListener: IGetOffersResponseListener
    override fun callGetOffers(
        context: Context,
        urlEndPoint: String,
        iGetOffersResponseListener: IGetOffersResponseListener
    ) {
        this.iGetOffersResponseListener = iGetOffersResponseListener
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
        val offersRes = parseResponse(response)
        when(offersRes.offerResponse.responseStatusHeader.statusCode){
            ErrorCode.success ->
                iGetOffersResponseListener.getOffersSuccess(offersRes)
            ErrorCode.error_0001->
                iGetOffersResponseListener.getOffersFailure(offersRes)
            else->
                iGetOffersResponseListener.getOffersFailure(offersRes)
        }
    }

    override fun onFailureResponse(response: String) {
        iGetOffersResponseListener.getOffersFailure(parseResponse(response))
    }

    override fun onUserNotConnected() {
        iGetOffersResponseListener.networkFailure()
    }
    private fun parseResponse(response: String): OffersMainResponse {
        return Gson().fromJson(
            response,
           OffersMainResponse::class.java
        )
    }
}